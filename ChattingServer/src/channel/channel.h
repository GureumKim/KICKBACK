#ifndef CHATTINGSERVER_CHANNEL_H
#define CHATTINGSERVER_CHANNEL_H

#include <iostream>
#include <set>
#include <boost/shared_ptr.hpp>

#include "../session/chat_participant.h"

class Channel
{
public:
    void join(chat_participant_ptr participant);

    void leave(chat_participant_ptr participant);

    void deliver(const std::string& msg);

    std::set<chat_participant_ptr> participants_;

private:
    enum { max_recent_msgs = 100 };
};

#endif //CHATTINGSERVER_CHANNEL_H
