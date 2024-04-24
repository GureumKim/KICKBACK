#include <boost/asio.hpp>
#include <iostream>
#include <string>
#include <unordered_map>
#include <set>

using boost::asio::ip::udp;

class chat_server {
public:
    chat_server(boost::asio::io_context &io_context, short port)
            : socket_(io_context, udp::endpoint(udp::v4(), port)) {
        do_receive();
    }

private:
    void do_receive() {
        socket_.async_receive_from(
                boost::asio::buffer(recv_buffer_), remote_endpoint_,
                [this](boost::system::error_code ec, std::size_t bytes_recvd) {
                    if (!ec && bytes_recvd > 0) {
                        std::string received_message(recv_buffer_, bytes_recvd);
                        process_message(received_message, remote_endpoint_);
                        do_receive();
                    }
                }
        );
    }

    void process_message(const std::string &message, const udp::endpoint &sender) {
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
    udp::endpoint remote_endpoint_;
    enum {
        max_length = 1024
    };
    char recv_buffer_[max_length];
    std::unordered_map<std::string, std::set<udp::endpoint>> rooms_;
};

int main() {
//    try {
//        boost::asio::io_context io_context;
//        chat_server server(io_context, 12345);
//
//        std::size_t thread_pool_size = std::thread::hardware_concurrency() * 2;
//        std::vector<std::thread> threads;
//        for(std::size_t i = 0; i < thread_pool_size; i++) {
//            threads.emplace_back([&io_context]() {
//                io_context.run();
//            });
//        }
//
//        // 모든 스레드 종료 될 때까지 대기
//        for(auto &t : threads) {
//            t.join();
//        }
//    }
//    catch(std::exception &e) {
//        std::cerr << "Exception: " << e.what() << '\n';
//    }

    try {
        boost::asio::io_context io_context;
        chat_server server(io_context, 12345);
        io_context.run();
    }
    catch (std::exception &e) {
        std::cerr << "Exception: " << e.what() << "\n";
    }
}