#include <set>
#include "chat_participant.cpp"

class channel
{
public:
    void join(chat_participant_ptr participant)
    {
        participants_.insert(participant);
    }

    void leave(chat_participant_ptr participant)
    {
        participants_.erase(participant);
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