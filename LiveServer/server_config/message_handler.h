#ifndef LIVESERVER_MESSAGE_HANDLER_H
#define LIVESERVER_MESSAGE_HANDLER_H

#include "message_unit.h"

enum Command {
    LIVESERVER,
    CLIENT,
    CREATE,
    JOIN,
    LEAVE,
    READY,
    START,
    ITEM,
    END,
    CHAT,
    MAP,
    RPOSITION,
    SPOSITION
};

class MessageHandler {
public:
    static void command(msgpack::object &deserialized);


};

#endif//LIVESERVER_MESSAGE_HANDLER_H
