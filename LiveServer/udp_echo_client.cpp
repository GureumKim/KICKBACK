#include <boost/asio.hpp>
#include <iostream>
#include <string>

using boost::asio::ip::udp;

class chat_client {
public:
    chat_client(boost::asio::io_context& io_context, const std::string& server, const std::string& port)
            : io_context_(io_context), socket_(io_context) {
        udp::resolver resolver(io_context_);
        receiver_endpoint_ = *resolver.resolve(udp::v4(), server, port).begin();
        socket_.open(udp::v4());
        do_receive();
    }

    void send(const std::string& message) {
        socket_.send_to(boost::asio::buffer(message), receiver_endpoint_);
    }

private:
    void do_receive() {
        socket_.async_receive_from(
                boost::asio::buffer(recv_buffer_), sender_endpoint_,
                [this](boost::system::error_code ec, std::size_t bytes_recvd) {
                    if (!ec && bytes_recvd > 0) {
                        std::cout.write(recv_buffer_, bytes_recvd);
                        std::cout << std::endl;
                    }
                    do_receive();
                });
    }

    boost::asio::io_context& io_context_;
    udp::socket socket_;
    udp::endpoint receiver_endpoint_;
    udp::endpoint sender_endpoint_;
    enum { max_length = 1024 };
    char recv_buffer_[max_length];
};

int main(int argc, char* argv[]) {
    try {
//        if (argc != 3) {
//            std::cerr << "Usage: chat_client <server> <port>\n";
//            return 1;
//        }

        boost::asio::io_context io_context;

        chat_client client(io_context, "localhost", "12345");

        std::thread t([&io_context]() { io_context.run(); });

        std::string message;
        while (getline(std::cin, message)) {
            client.send(message);
        }

        t.join();
    } catch (std::exception& e) {
        std::cerr << "Exception: " << e.what() << "\n";
    }

    return 0;
}
