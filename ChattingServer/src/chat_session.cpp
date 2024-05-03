#include <boost/asio.hpp>
#include <boost/enable_shared_from_this.hpp>
#include "channel.cpp"
#include "message.h"

using boost::asio::ip::tcp;

class chat_session
        : public chat_participant,
          public boost::enable_shared_from_this<chat_session> {
public:
    chat_session(boost::asio::io_context &io_context, channel *channel, int channel_index)
        : socket_(io_context),
          channel_(channel),
          channel_index_(channel_index) {
    }

    tcp::socket &socket() {
        return socket_;
    }

    void start() {
        channel_->join(shared_from_this());

        read_message();
    }

    void read_message() {
        socket_.async_read_some(boost::asio::buffer(read_msg_, max_length),
                                boost::bind(&chat_session::handle_read_message, this,
                                            boost::asio::placeholders::error,
                                            boost::asio::placeholders::bytes_transferred));
    }

    void handle_read_message(const boost::system::error_code &error, size_t bytes_transferred) {
        if (!error) {
            msgpack::sbuffer sbuf;
            msgpack::object_handle oh =
                    msgpack::unpack(read_msg_, bytes_transferred);

            msgpack::object deserialized = oh.get();

            Message message;

            std::stringstream buffer;
            msgpack::pack(buffer, deserialized);

            switch (int command = message.command(deserialized)) {
                case -1:
                    move_to_channel(0);
                    break;
                case 0:
                    channel_->deliver(buffer.str());
                    break;
                default:
                    std::cout << command << std::endl;
                    move_to_channel(command);
                    break;
            }
        }
    }

    int get_channel_index() const {
        return channel_index_;
    }

private:
    void deliver(const std::string &msg) override {
        bool write_in_progress = !write_msgs_.empty();
        write_msgs_.push_back(msg);
        if (!write_in_progress) {
            do_write();
        }
    }

    void do_write() {
        boost::asio::async_write(socket_,
                                 boost::asio::buffer(write_msgs_.front().data(),
                                                     write_msgs_.front().length()),
                                 boost::bind(&chat_session::handle_write, shared_from_this(),
                                             boost::asio::placeholders::error));
    }

    void handle_write(const boost::system::error_code &error) {
        if (!error) {
            write_msgs_.pop_front();
            if (!write_msgs_.empty()) {
                do_write();
            }

            read_message();
        } else {
            channel_->leave(shared_from_this());
        }
    }

    void move_to_channel(int new_channel_index) {

        // 새 채널을 가져옵니다.
        channel &new_channel = channel_list::get_instance().get_channel(new_channel_index);

        // 새 채널에 세션을 추가합니다.
        new_channel.join(shared_from_this());

        // 현재 채널에서 세션을 제거합니다.
        channel_->leave(shared_from_this());

        // chat_session의 현재 채널 참조를 업데이트합니다.
        channel_ = &new_channel;
        channel_index_ = new_channel_index;

        read_message();
    }


    enum { max_length = 1024 };

    tcp::socket socket_;
    channel *channel_;
    char read_msg_[max_length];
    int channel_index_;
    std::deque<std::string> write_msgs_;
};

typedef boost::shared_ptr<chat_session> chat_session_ptr;
