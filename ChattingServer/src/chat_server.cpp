#include <boost/bind/bind.hpp>
#include <boost/asio.hpp>
#include "chat_session.cpp"

class chat_server
{
public:
    chat_server(boost::asio::io_context& io_context,
                const boost::asio::ip::tcp::endpoint& endpoint)
            : io_context_(io_context),
              acceptor_(io_context, endpoint)
    {
        channel_ = channel_list::get_instance().get_channel(0);
        start_accept();
    }

    void start_accept()
    {
        chat_session_ptr new_session(new chat_session(io_context_, channel_, 0));
        acceptor_.async_accept(new_session->socket(),
                               boost::bind(&chat_server::handle_accept, this, new_session,
                                           boost::asio::placeholders::error));
    }

    void handle_accept(chat_session_ptr session,
                       const boost::system::error_code& error)
    {
        if (!error)
        {
            // 클라이언트의 엔드포인트 정보를 얻습니다.
            tcp::endpoint remote_ep = session->socket().remote_endpoint();
            // IP 주소와 포트 번호를 문자열로 변환합니다.
            std::string client_info = remote_ep.address().to_string() + ":" + std::to_string(remote_ep.port());
            // 현재 참가중인 채팅방 인덱스 정보
            int channel_index = session->get_channel_index();
            // 클라이언트의 접속 정보를 출력합니다.
            std::cout << "클라이언트 접속: " << client_info << " / 현재 참여중인 채팅방: " << channel_index << std::endl;

            session->start();
        }

        start_accept();
    }

private:
    boost::asio::io_context& io_context_;
    tcp::acceptor acceptor_;
    channel channel_;
};

typedef boost::shared_ptr<chat_server> chat_server_ptr;
