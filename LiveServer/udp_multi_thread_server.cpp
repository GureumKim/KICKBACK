#include <boost/asio.hpp>
#include <iostream>
#include <string>
#include <unordered_map>
#include <vector>
#include <thread>
#include <mutex>
#include <queue>

using boost::asio::ip::udp;

// 스레드세이프 큐
template<typename T>
class ThreadSafeQueue {
public:
    ThreadSafeQueue() {}
    ThreadSafeQueue(const ThreadSafeQueue& other) {
        std::lock_guard<std::mutex> lock(other.mutex_);
        queue_ = other.queue;
    }

    void push(T value) {
        std::lock_guard<std::mutex> lock(mutex_);
        queue_.push(std::move(value));
        cond_.notify_one();
    }

    void wait_and_pop(T& value) {
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

std::mutex queue_mutex;
std::condition_variable queue_cond;
//std::queue<std::string> message_queue;
ThreadSafeQueue<std::unordered_map<udp::endpoint, std::string>> message_queue;

udp::endpoint remote_endpoint_;
enum {
    max_length = 1024
};
char recv_buffer_[max_length];


// 채팅방 관리 위한 구조체
struct ChatRoom {
    std::vector<udp::endpoint> participants;
};

std::unordered_map<std::string, ChatRoom> chat_rooms;

// 요청 받기
class receiver {
public:
    receiver(boost::asio::io_context &io_context, short port)
            : socket_(io_context, udp::endpoint(udp::v4(), port)) {
        do_receive();
    }

private:
    void do_receive() {
        // 비동기적으로 데이터 수신 위해 socket_객체에 호출
        // 데이터 수신 할 때까지 현재 스레드 블록x
        socket_.async_receive_from(
                // 수신된 데이터를 저장할 버퍼, recv_buffer_는 실제 데이터가 저장될 메모리 영역임
                // remote_endpoint_는 데이터 보낸 엔드포인트 주소와 포트 저장할 객체
                boost::asio::buffer(recv_buffer_), remote_endpoint_,
                // async_receive_from()의 콟백함수로, 비동기 작업이 완료(데이터 수신시)되면 실행
                // [this]: 람다 캡처 리스트로, 현재 객체를 람다 내부에서 사용하게 함
                // 이는 클래스 멤버변수나 함수에 접근 시 사용
                [this](boost::system::error_code ec, std::size_t bytes_recvd) {
                    if (!ec && bytes_recvd > 0) {
                        std::string received_message(recv_buffer_, bytes_recvd);
                        // mutex lock 후 message_queue에 넣기
                        {
                            std::lock_guard<std::mutex> lock(queue_mutex);
                            message_queue.emplace(received_message, bytes_recvd);
                            std::cout << received_message << std::endl;
                        }
                        queue_cond.notify_one();
                        do_receive();
                    }
                }
        );
    }

    udp::socket socket_;
};

// 요청 처리하기
class worker {
public:
    worker(boost::asio::io_context &io_context, short port)
            : socket_(io_context, udp::endpoint(udp::v4(), port)),
              work_(boost::asio::make_work_guard(io_context)) {
        do_work(io_context);
    }

private:
    static void process_message(boost::asio::io_context &io_context, const std::string &message) {
        // TODO: 실제 메시지 처리 로직
        std::cout << "Processing message: " << message << std::endl;
    }

    static void do_work(boost::asio::io_context &io_context) {
        std::thread([&]() {
            while (true) {
                std::cout << "asdf" << std::endl;
                // TODO: 그냥 lock, unique_lock 차이 확인
                std::unique_lock<std::mutex> lock(queue_mutex);

//            queue_cond.wait(lock, []() -> bool { return !message_queue.empty(); });
                queue_cond.wait(lock, [] { return !message_queue.empty(); });

                std::string message = message_queue.front();
                message_queue.pop();
                lock.unlock();

                io_context.post([&io_context, message] { return process_message(io_context, message); });
            }
        }).detach();
    }

    udp::socket socket_;
    boost::asio::executor_work_guard<boost::asio::io_context::executor_type> work_;
};

// 받은 메시지 찾아서 처리하기
//void process_message(boost::asio::io_context& io_context, udp::socket& socket) {
//    while(true) {
//        std::string message;
//        {   // lock_guard 소멸 위해 블록 지정
//            std::lock_guard<std::mutex> lock(queue_mutex);
//            if(!message_queue.empty()) {
//                message = message_queue.front();
//                message_queue.pop();
//            }
//        }
//
//        // 처리할 메시지가 없다면 계속 진행
//        if(message.empty()) {
//            continue;
//        }
//
//        // 메시지 형식 분석(CREATE, JOIN, MESSAGE)
//        std::istringstream iss(message);
//        std::string command, room_name, msg;
//        iss >> command;
//
//        if(command == "CREATE") {
//            iss >> room_name;
//            chat_rooms[room_name] = ChatRoom();
//            std::cout << "Room created: " << room_name << std::endl;
//        }
//        else if(command == "JOIN") {
//            iss >> room_name;
//            chat_rooms[room_name] = ChatRoom();
//            std::cout << "Room joined: " << room_name << std::endl;
//        }
//        else if(command == "MESSAGE") {
//            iss >> room_name >> msg;
//
//            std::cout << "MSG send(" << room_name << "): " << msg << '\n';
//
//            for(const auto &participant : chat_rooms[room_name].participants) {
////                if(participant != socket.local_endpoint()) {
//                    socket.send_to(boost::asio::buffer(msg), participant);
////                }
//            }
//        }
//        else {
////            socket.send_to(boost::asio::buffer("Incorrect message"), socket.endpoint);
//        }
//    }
//}

// 메시지 받기
//void server(boost::asio::io_context &io_context, short port) {
//    udp::socket socket(io_context, udp::endpoint(udp::v4(), port));
//    char data[1024];
//
//    while (true) {
//        udp::endpoint sender_endpoint;
//        size_t length = socket.receive_from(boost::asio::buffer(data), sender_endpoint);
//
//        // 받은 메시지를 큐에 추가
//        {
//            std::lock_guard<std::mutex> lock(queue_mutex);
//            message_queue.emplace(data, length);
//        }
//    }
//}

int main() {
    try {
        // IO 컨텍스트 객체 생성
        boost::asio::io_context io_context;



        // 1개 스레드는 수신용
        receiver receiver_(io_context, 12345);
        std::thread receiver_thread([&io_context]() { io_context.run(); });
        receiver_thread.join();

        // 나머지(하드웨어 스레드 수 * 2) 스레드는 처리
        std::cout << "asdf" << std::endl;
        worker worker_(io_context, 12345);
        std::size_t thread_pool_size = std::thread::hardware_concurrency() * 2;
        std::cout << thread_pool_size << std::endl;
        std::vector<std::thread> threads;
        for(std::size_t i = 0; i < thread_pool_size; i++) {
            threads.emplace_back([&io_context] { io_context.run(); });
        }
        for(auto &t : threads) {
            t.join();
        }


//        std::thread server_thread([&io_context]() { server(io_context, 12345); });
//
//        udp::socket socket(io_context);
//
//        // 요청 처리 스레드 시작
//        std::thread processing_thread([&io_context, &socket]() { process_message(io_context, socket); });

//        server_thread.join();
//        processing_thread.join();
    }
    catch (std::exception &e) {
        std::cerr << e.what() << std::endl;
    }

    return 0;
}