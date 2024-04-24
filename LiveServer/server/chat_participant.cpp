#include <boost/shared_ptr.hpp>
#include <boost/enable_shared_from_this.hpp>
#include "chat_message.hpp"
/*
 * 채팅 참가자를 나타내는 추상 기반 클래스
 * 채팅 참가자가 가져야 할 기본적인 인터페이스
 */

class chat_participant
{
public:
    virtual ~chat_participant() {}
    virtual void deliver(const chat_message& msg) = 0;
};

typedef boost::shared_ptr<chat_participant> chat_participant_ptr;