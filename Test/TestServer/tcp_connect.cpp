#include <boost/bind/bind.hpp>
#include <boost/asio.hpp>
#include <msgpack.hpp>
#include "chat_message.hpp"

using boost::asio::ip::tcp;

class tcp_connect
{
public:
    tcp_connect(boost::asio::io_context& io_context,
                const tcp::resolver::results_type& endpoints)
            : io_context_(io_context),
              socket_(io_context)
    {
        boost::asio::async_connect(socket_, endpoints,
                                   boost::bind(&tcp_connect::handle_connect, this,
                                               boost::asio::placeholders::error));
    }

private:
    void handle_connect(const boost::system::error_code& error)
    {
        if(!error)
        {
            // 연결 성공 시, 비즈니스 서버에 메시지 전송
            std::string message = "LIVESERVER\n";
            msgpack::sbuffer sbuf;
            msgpack::pack(sbuf, message);

            boost::asio::async_write(socket_,
                                     boost::asio::buffer(sbuf.data(), sbuf.size()),
                                     boost::bind(&tcp_connect::handle_write, this,
                                                 boost::asio::placeholders::error,
                                                 boost::asio::placeholders::bytes_transferred));

            read_header();
        }
    }

    void handle_write(const boost::system::error_code& error,
                      size_t)
    {
        if (!error)
        {
            std::cout << "send complete" << std::endl;
        }
        else
        {
            std::cout << error.message() << std::endl;
        }
    }

    void read_header()
    {
        boost::asio::async_read(socket_,
                                boost::asio::buffer(read_msg_.data(), chat_message::header_length),
                                                    boost::bind(&tcp_connect::handle_read_body, this,
                                                                boost::asio::placeholders::error));
    }

    void handle_read_body(const boost::system::error_code& error)
    {
        if(!error)
        {
            std::cout << "[TCP]Server: " << read_msg_.get_message() << std::endl;
        }
    }

    boost::asio::io_context& io_context_;
    tcp::socket socket_;
    chat_message read_msg_;
    chat_message write_msg_;
};