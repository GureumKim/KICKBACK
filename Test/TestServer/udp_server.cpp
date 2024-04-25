#include <boost/bind/bind.hpp>
#include <boost/array.hpp>
#include "client_manager.cpp"

class udp_server {
public:
    udp_server(boost::asio::io_context &io_context)
            : socket_(io_context, udp::endpoint(udp::v4(), 13)) {
        start_receive();
    }

private:
    void start_receive() {
        socket_.async_receive_from(
                boost::asio::buffer(recv_buffer_), remote_endpoint_,
                boost::bind(&udp_server::handle_receive, this,
                            boost::asio::placeholders::error,
                            boost::asio::placeholders::bytes_transferred));
    }

    void handle_receive(const boost::system::error_code &error,
                        std::size_t) {
        if (!error) {
            client_manager::get_instance().add_client(remote_endpoint_);

            std::cout << "[UDP]Client connected: " << remote_endpoint_ << std::endl;
            client_manager::get_instance().print_client_list();

            boost::shared_ptr<std::string> message(
                    new std::string("exit\n")
            );

            auto clients = client_manager::get_instance().get_clients();

            for (auto &client_endpoint: clients) {
                socket_.async_send_to(boost::asio::buffer(*message), client_endpoint,
                                      boost::bind(&udp_server::handle_send, this, message,
                                                  boost::asio::placeholders::error,
                                                  boost::asio::placeholders::bytes_transferred));
            }

            start_receive();
        }
    }

    void handle_send(boost::shared_ptr<std::string> message,
                     const boost::system::error_code &,
                     std::size_t) {
        std::cout << "send" << std::endl;
    }

    udp::socket socket_;
    udp::endpoint remote_endpoint_;
    boost::array<char, 1> recv_buffer_;
};