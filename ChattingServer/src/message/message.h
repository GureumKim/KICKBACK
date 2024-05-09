#pragma once

#ifndef CHATTING_SERVER_MESSAGE_H
#define CHATTING_SERVER_MESSAGE_H

#include <iostream>

#include "../../include/msgpack.hpp"
#include "../session/chat_session.h"
#include "message_unit.h"

class ChatSession;

class Message {
    enum Command {
        LIVESERVER, CLIENT, CREATE, JOIN, LEAVE, READY, START, ITEM, END, CHAT, MAP
    };

public:
    static int command(std::shared_ptr<ChatSession>& session, msgpack::object &deserialized);
};

#endif//CHATTING_SERVER_MESSAGE_H
