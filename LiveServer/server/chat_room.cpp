#include <algorithm>
#include <deque>
#include <set>
#include <boost/bind/bind.hpp>
#include <boost/shared_ptr.hpp>
#include <boost/asio.hpp>
#include "chat_participant.cpp"

using boost::asio::ip::tcp;

//----------------------------------------------------------------------

typedef std::deque<chat_message> chat_message_queue;

//----------------------------------------------------------------------

/*
 * 채팅방
 * 채팅 참가자들 관리하고 메시지 전송
 */
class chat_room
{
public:
    /*
     * 새로운 참가자 채팅방에 추가
     */
    void join(chat_participant_ptr participant)
    {
        participants_.insert(participant);
        // First ~ Last까지 Func실행
        std::for_each(recent_msgs_.begin(), recent_msgs_.end(),
                      boost::bind(&chat_participant::deliver,
                                  participant, boost::placeholders::_1));
    }

    void leave(chat_participant_ptr participant)
    {
        participants_.erase(participant);
    }

    void deliver(const chat_message& msg)
    {
        recent_msgs_.push_back(msg);
        while (recent_msgs_.size() > max_recent_msgs)
            recent_msgs_.pop_front();

        std::for_each(participants_.begin(), participants_.end(),
                      boost::bind(&chat_participant::deliver,
                                  boost::placeholders::_1, boost::ref(msg)));
    }

private:
    std::set<chat_participant_ptr> participants_;
    enum { max_recent_msgs = 100 };
    chat_message_queue recent_msgs_;
};