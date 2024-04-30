#include <boost/asio.hpp>
#include <iostream>
#include <string>
#include <unordered_map>
#include <thread>
#include <mutex>
#include <queue>
#include <set>
#include "server_config/tcp_connect.cpp"

using boost::asio::ip::udp;

// 스레드세이프 큐
template<typename T>
class ThreadSafeQueue {
public:
    ThreadSafeQueue() {}

    ThreadSafeQueue(const ThreadSafeQueue &other) {
        std::lock_guard<std::mutex> lock(other.mutex_);
        queue_ = other.queue;
    }

    void push(T value) {
        std::lock_guard<std::mutex> lock(mutex_);
        queue_.push(std::move(value));
        cond_.notify_one();
    }

    void wait_and_pop(T &value) {
        std::unique_lock<std::mutex> lock(mutex_);
        cond_.wait(lock, [this] { return !queue_.empty(); });
        value = std::move(queue_.front());
        queue_.pop();
    }

private:
    std::queue<T> queue_;
    mutable std::mutex mutex_;
    std::condition_variable cond_;
};

enum {
    max_length = 1024
};

ThreadSafeQueue<std::pair<udp::endpoint, std::string>> message_queue;
udp::endpoint remote_endpoint;

char recv_buffer[max_length];

class LiveServer {
public:
    LiveServer(boost::asio::io_context &io_context, short port)
            : socket_(io_context, udp::endpoint(udp::v4(), port)) {
    }

    // 수신 스레드가 참조
    void startReceive() {
        // 비동기적으로 데이터 수신 위해 socket_객체에 호출
        // 데이터 수신 할 때까지 현재 스레드 블록x
        socket_.async_receive_from(
                // 수신된 데이터를 저장할 버퍼, recv_buffer_는 실제 데이터가 저장될 메모리 영역임
                // remote_endpoint_는 데이터 보낸 엔드포인트 주소와 포트 저장할 객체
                boost::asio::buffer(recv_buffer), remote_endpoint,
                // async_receive_from()의 콟백함수로, 비동기 작업이 완료(데이터 수신시)되면 실행
                // [this]: 람다 캡처 리스트로, 현재 객체를 람다 내부에서 사용하게 함
                // 이는 클래스 멤버변수나 함수에 접근 시 사용
                [this](boost::system::error_code ec, std::size_t bytes_recvd) {
                    if (!ec && bytes_recvd > 0) {
                        std::string received_message(recv_buffer, bytes_recvd);
                        // mutex lock 후 message_queue에 넣기
                        message_queue.push(std::make_pair(remote_endpoint, received_message));
                        startReceive();
                    }
                }
        );
    }

    // 작업 스레드가 참조
    void startWork(boost::asio::io_context& io_context) {
        std::thread([&]() {
            while (true) {
                // TODO: 그냥 lock, unique_lock 차이 확인
                std::pair<udp::endpoint, std::string> recv_data;
                message_queue.wait_and_pop(recv_data);
                std::cout << recv_data.first << " " << recv_data.second << std::endl;

                udp::endpoint sender = recv_data.first;
                std::string message = recv_data.second;

                io_context.post([&io_context, sender, message, this] { return process_message(io_context, sender, message); });
            }
        }).detach();
    }

private:
    void process_message(boost::asio::io_context &io_context, const udp::endpoint &sender, const std::string &message) {
        // TODO: 실제 메시지 처리 로직
        std::cout << "Processing message(" << sender << "): " << message << std::endl;

        // Example message format: "CREATE:a", "JOIN:a", "MSG:a:Hello"
        auto command_delimiter_pos = message.find(':');
        auto room_delimiter_pos = message.find(':', command_delimiter_pos + 1);
        std::string command = message.substr(0, command_delimiter_pos);
        std::string room_name = message.substr(command_delimiter_pos + 1,
                                               room_delimiter_pos - command_delimiter_pos - 1);
        std::string content = message.substr(room_delimiter_pos + 1);
        std::cout << command << ", " << room_name << ", " << content << std::endl;

        if (command == "CREATE") {
            rooms_[room_name].insert(sender);
        } else if (command == "JOIN") {
            rooms_[room_name].insert(sender);
        } else if (command == "MSG") {
            for (const auto &participant: rooms_[room_name]) {
                if (participant != sender) {
                    socket_.send_to(boost::asio::buffer(content), participant);
                }
            }
        } else {
            socket_.send_to(boost::asio::buffer("Incorrect message"), sender);
        }
    }

    udp::socket socket_;
    std::unordered_map<std::string, std::set<udp::endpoint>> rooms_;
};


int main() {
    try {
        // IO 컨텍스트 객체 생성
        boost::asio::io_context io_context;

//        LiveServer live_server(io_context, 12345);
//        std::size_t thread_pool_size = (std::thread::hardware_concurrency() * 2) + 1;
//        std::vector<std::thread> threads;
//        // 수신 스레드
//        live_server.startReceive();
//        threads.emplace_back([&io_context] { io_context.run(); });
//
//        // 처리 스레드
//        live_server.startWork(io_context);
//        for(std::size_t i = 1; i < thread_pool_size; i++) {
//            threads.emplace_back([&io_context] { io_context.run(); });
//        }
//        for(auto& t : threads) {
//            t.join();
//        }

        ////////////////////////////////////////////////////////////////////////////////////////

        tcp::resolver resolver(io_context);
        auto endpoints = resolver.resolve("localhost", "1370");
        tcp_connect tcpConnect(io_context, endpoints);

        std::thread tcp_connect([&io_context]() {
            std::cout << "tcp run" << std::endl;
            io_context.run();
        });

        tcp_connect.join();
    }
    catch (std::exception &e) {
        std::cerr << e.what() << std::endl;
    }

    return 0;
}