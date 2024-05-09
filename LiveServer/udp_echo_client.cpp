#include <boost/asio.hpp>
#include <iostream>
#include <string>

#include "src/model/message_form.h"

using boost::asio::ip::udp;

class chat_client {
public:
    chat_client(boost::asio::io_context &io_context, const std::string &server, const std::string &port)
        : io_context_(io_context), socket_(io_context) {
        udp::resolver resolver(io_context_);
        receiver_endpoint_ = *resolver.resolve(udp::v4(), server, port).begin();
        socket_.open(udp::v4());
        do_receive();
    }

    void send(const msgpack::sbuffer &sbuf) {
        socket_.async_send_to(
                boost::asio::buffer(sbuf.data(), sbuf.size()), receiver_endpoint_,
                [](boost::system::error_code ec, std::size_t bytes_sent) {
                    if (ec) {
                        std::cerr << "Error sending data: " << ec.message() << std::endl;
                    }
                });
        //        socket_.send_to(boost::asio::buffer(sbuf), receiver_endpoint_);
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

    boost::asio::io_context &io_context_;
    udp::socket socket_;
    udp::endpoint receiver_endpoint_;
    udp::endpoint sender_endpoint_;
    enum {
        max_length = 1024
    };
    char recv_buffer_[max_length];
};

int main(int argc, char *argv[]) {
    try {
//                if (argc != 3) {
//                    std::cerr << "Usage: chat_client <server> <port>\n";
//                    return 1;
//                }

        boost::asio::io_context io_context;

        chat_client client(io_context, "localhost", "1234");

        std::thread t([&io_context]() { io_context.run(); });

        std::string message;
        while (getline(std::cin, message)) {
            msgpack::sbuffer sbuf;

            if(message.empty()) {
                continue;
            }

            int command = std::stoi(message);
            switch (command) {
                case Command::CREATE: {
                    MessageForm message_form(Command::CREATE);
                    sbuf = message_form.packMessage();
                    break;
                }
                case Command::JOIN: {
                    int channel_number;
                    std::cout << "channel number: ";
                    std::cin >> channel_number;
                    std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n'); // 입력 버퍼 비우기

                    MessageForm message_form(Command::JOIN, channel_number);
//                    MessageForm message_form;
//                    message_form.setCommand(Command::JOIN);
//                    message_form.setChannelNumber(channel_number);
                    sbuf = message_form.packMessage();
                    break;
                }
                case Command::START: {
                    MessageForm message_form(Command::START);
                    sbuf = message_form.packMessage();
                    break;
                }
                case Command::MESSAGE: {
                    int channel_number;
                    std::cout << "channel number: ";
                    std::cin >> channel_number;
                    std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n'); // 입력 버퍼 비우기

                    std::string msg;
                    std::cout << "message: ";
                    std::cin >> msg;
                    std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n'); // 입력 버퍼 비우기

                    MessageForm message_form(Command::MESSAGE, channel_number, msg);
                    sbuf = message_form.packMessage();
                    break;
                }
            }

            client.send(sbuf);
        }

        t.join();
    } catch (std::exception &e) {
        std::cerr << "Exception: " << e.what() << "\n";
    }

    return 0;
}
