#include <boost/asio.hpp>
#include <boost/enable_shared_from_this.hpp>
#include "channel.cpp"

using boost::asio::ip::tcp;

class chat_session
        : public chat_participant,
          public boost::enable_shared_from_this<chat_session>
{
public:
    chat_session(boost::asio::io_context& io_context, channel& channel)
            : socket_(io_context),
              channel_(channel)
    {
    }

    tcp::socket& socket()
    {
        return socket_;
    }

    void start()
    {
        channel_.join(shared_from_this());
        boost::asio::async_read(socket_,
                                boost::asio::buffer(read_msg_.data(), chat_message::header_length),
                                boost::bind(
                                        &chat_session::handle_read_header, shared_from_this(),
                                        boost::asio::placeholders::error));
    }

    void deliver(const chat_message& msg)
    {
        bool write_in_progress = !write_msgs_.empty();
        write_msgs_.push_back(msg);
        if (!write_in_progress)
        {
            boost::asio::async_write(socket_,
                                     boost::asio::buffer(write_msgs_.front().data(),
                                                         write_msgs_.front().length()),
                                     boost::bind(&chat_session::handle_write, shared_from_this(),
                                                 boost::asio::placeholders::error));
        }
    }

    void handle_read_header(const boost::system::error_code& error)
    {
        if (!error && read_msg_.decode_header())
        {
            boost::asio::async_read(socket_,
                                    boost::asio::buffer(read_msg_.body(), read_msg_.body_length()),
                                    boost::bind(&chat_session::handle_read_body, shared_from_this(),
                                                boost::asio::placeholders::error));
        }
        else
        {
            channel_.leave(shared_from_this());
        }
    }

    void handle_read_body(const boost::system::error_code& error)
    {
        if (!error)
        {
            channel_.deliver(read_msg_);
            boost::asio::async_read(socket_,
                                    boost::asio::buffer(read_msg_.data(), chat_message::header_length),
                                    boost::bind(&chat_session::handle_read_header, shared_from_this(),
                                                boost::asio::placeholders::error));
        }
        else
        {
            channel_.leave(shared_from_this());
        }
    }

    void handle_write(const boost::system::error_code& error)
    {
        if (!error)
        {
            write_msgs_.pop_front();
            if (!write_msgs_.empty())
            {
                boost::asio::async_write(socket_,
                                         boost::asio::buffer(write_msgs_.front().data(),
                                                             write_msgs_.front().length()),
                                         boost::bind(&chat_session::handle_write, shared_from_this(),
                                                     boost::asio::placeholders::error));
            }
        }
        else
        {
            channel_.leave(shared_from_this());
        }
    }

private:
    tcp::socket socket_;
    channel& channel_;
    chat_message read_msg_;
    chat_message_queue write_msgs_;
};

typedef boost::shared_ptr<chat_session> chat_session_ptr;