#ifndef CHATTINGSERVER_MESSAGE_UNIT_H
#define CHATTINGSERVER_MESSAGE_UNIT_H

#include "../../include/msgpack.hpp"

class MessageUnit {
public:
    MSGPACK_DEFINE(command, channelIndex, userName, message);

    int get_command() const;

    int get_channelIndex() const;

    std::string get_userName();

    std::string get_message();

private:
    int command;
    int channelIndex;
    std::string userName;
    std::string message;
};


#endif //CHATTINGSERVER_MESSAGE_UNIT_H
