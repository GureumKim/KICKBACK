#ifndef CHATTINGSERVER_CHAT_SERVER_H
#define CHATTINGSERVER_CHAT_SERVER_H

#include <boost/bind/bind.hpp>
#include <boost/asio.hpp>

#include "../channel/channel_list.h"
#include "../session/chat_session.h"

class ChatServer
{
public:
    ChatServer(boost::asio::io_context& io_context,
                const boost::asio::ip::tcp::endpoint& endpoint);

    void start_accept();

    void handle_accept(chat_session_ptr session,
                       const boost::system::error_code& error);

private:
    boost::asio::io_context& io_context_;
    tcp::acceptor acceptor_;
    Channel channel_;
};

typedef boost::shared_ptr<ChatServer> chat_server_ptr;


#endif //CHATTINGSERVER_CHAT_SERVER_H
