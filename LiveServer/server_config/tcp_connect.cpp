#include <iostream>
#include <vector>
#include <boost/bind/bind.hpp>
#include <boost/asio.hpp>

#include "message_handler.h"

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

    void write_message(msgpack::sbuffer &buffer) {
        boost::asio::async_write(socket_,
                                 boost::asio::buffer(buffer.data(), buffer.size()),
                                 boost::bind(&tcp_connect::handle_write_message, this,
                                             boost::asio::placeholders::error,
                                             boost::asio::placeholders::bytes_transferred));
    }

    void read_message() {
        socket_.async_read_some(boost::asio::buffer(data_, max_length),
                                boost::bind(&tcp_connect::handle_read_message, this,
                                            boost::asio::placeholders::error,
                                            boost::asio::placeholders::bytes_transferred));
    }

private:
    void handle_connect(const boost::system::error_code &error) {
        if (!error) {
            // 비즈니스 서버 최초 접속시 라이브 서버 확인용 메시지
            msgpack::sbuffer buffer;
            msgpack::packer<msgpack::sbuffer> packer(&buffer);

            packer.pack_array(3);

            packer.pack(0);
            packer.pack(std::string("LiveServer"));
            packer.pack(std::string("\n"));

            write_message(buffer);
            read_message();
        }
    }

    void handle_write_message(const boost::system::error_code &error, size_t) {
        if (!error) {
            std::cout << "send complete" << std::endl;
        } else {
            std::cout << error.message() << std::endl;
        }
    }

    void handle_read_message(const boost::system::error_code& error, size_t bytes_transferred) {
        if (!error)
        {
            msgpack::object_handle oh = msgpack::unpack(data_, bytes_transferred);
            msgpack::object deserialized = oh.get();
            MessageHandler::command(deserialized);
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
