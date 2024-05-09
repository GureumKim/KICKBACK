#ifndef LIVESERVER_MESSAGE_UNIT_H
#define LIVESERVER_MESSAGE_UNIT_H

#include <string>
#include <msgpack.hpp>

class MessageUnit {
private:
    int command;
    int channel_index;

public:
    MSGPACK_DEFINE(command, channel_index);

    int get_command();
    int get_channel_index();
};


#endif//LIVESERVER_MESSAGE_UNIT_H
