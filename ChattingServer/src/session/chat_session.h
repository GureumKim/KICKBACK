#pragma once

#ifndef CHATTING_SERVER_CHAT_SESSION_H
#define CHATTING_SERVER_CHAT_SESSION_H

#include <boost/asio.hpp>
#include <boost/enable_shared_from_this.hpp>

#include "../channel/channel_list.h"
#include "../message/message.h"

using boost::asio::ip::tcp;

class ChatSession
        : public ChatParticipant,
          public boost::enable_shared_from_this<ChatSession> {
public:
    ChatSession(boost::asio::io_context &io_context, Channel *channel, int channel_index);

    tcp::socket &socket();

    void start();

    void read_message();

    void handle_read_message(const boost::system::error_code &error, size_t bytes_transferred);

    int get_channel_index() const;

    void move_to_channel(int new_channel_index);

private:
    void deliver(const std::string &msg) override;

    void do_write();

    void handle_write(const boost::system::error_code &error);

    enum { max_length = 1024 };

    tcp::socket socket_;
    Channel *channel_;
    int channel_index_;
    std::array<char, max_length> receiveBuffer_;
    std::deque<std::string> write_msgs_;
};

typedef boost::shared_ptr<ChatSession> chat_session_ptr;

#endif