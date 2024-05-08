#include <set>
#include "chat_participant.cpp"

class channel
{
public:
    void join(chat_participant_ptr participant)
    {
        participants_.insert(participant);

        // 클라이언트 접속시 채널에 클라이언트가 들어가는지 확인용
        // std::cout << participants_.size() << std::endl;
    }

    void leave(chat_participant_ptr participant)
    {
        participants_.erase(participant);

        // 클라이언트 종료시 채널 내부에 남아 있는 인원 확인용
        // std::cout << participants_.size() << std::endl;
    }

    void deliver(const std::string& msg) {
        for (auto participant: participants_) {
            participant->deliver(msg);
        }
    }

private:
    std::set<chat_participant_ptr> participants_;
    enum { max_recent_msgs = 100 };
};