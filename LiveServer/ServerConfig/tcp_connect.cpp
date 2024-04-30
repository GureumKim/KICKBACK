#include <iostream>
#include <vector>
#include <boost/bind/bind.hpp>
#include <boost/asio.hpp>
#include "packet.cpp"
#include "message_handler.cpp"

using boost::asio::ip::tcp;

class tcp_connect {
public:
    tcp_connect(boost::asio::io_context &io_context,
                const tcp::resolver::results_type &endpoints)
            : io_context_(io_context),
              socket_(io_context) {
        boost::asio::async_connect(socket_, endpoints,
                                   boost::bind(&tcp_connect::handle_connect, this,
                                               boost::asio::placeholders::error));
    }

private:
    void handle_connect(const boost::system::error_code &error) {
        if (!error) {
            std::string message = "{userName: \"LIVESERVER\"}\n";

            msgpack::sbuffer buffer = packet::packing(message);

            boost::asio::async_write(socket_,
                                     boost::asio::buffer(buffer.data(), buffer.size()),
                                     boost::bind(&tcp_connect::handle_write, this,
                                                 boost::asio::placeholders::error,
                                                 boost::asio::placeholders::bytes_transferred));

            read_message();
        }
    }

    void handle_write(const boost::system::error_code &error, size_t) {
        if (!error) {
            std::cout << "send complete" << std::endl;
        } else {
            std::cout << error.message() << std::endl;
        }
    }

    void read_message() {
        socket_.async_read_some(boost::asio::buffer(data_, max_length),
                                boost::bind(&tcp_connect::handle_read_message, this,
                                            boost::asio::placeholders::error,
                                            boost::asio::placeholders::bytes_transferred));
    }

    void handle_read_message(const boost::system::error_code& error, size_t bytes_transferred) {
        if (!error)
        {
            msgpack::object_handle oh =
                    msgpack::unpack(data_, bytes_transferred);

            msgpack::object deserialized = oh.get();

            std::string str;
            deserialized.convert(str);

            std::cout << str << std::endl;
        }
        else
        {
            delete this;
        }

        read_message();
    }

    boost::asio::io_context &io_context_;
    tcp::socket socket_;
    enum { max_length = 1024 };
    char data_[max_length];
};
