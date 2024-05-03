#ifndef CHATTING_SERVER_MESSAGE_H
#define CHATTING_SERVER_MESSAGE_H

#include <iostream>
#include "channel_list.cpp"
#include "../include/msgpack.hpp"

class chat_session;

class MessageUnit {
public:
    MSGPACK_DEFINE(command, channelIndex, userName, message);

    int get_command() const {
        return command;
    }

    int get_channelIndex() const {
        return channelIndex;
    }

    std::string get_userName() {
        return userName;
    }

    std::string get_message() {
        return message;
    }

private:
    int command;
    int channelIndex;
    std::string userName;
    std::string message;
};

class Message {
    enum Command {
        JOIN, LEAVE, SEND
    };

public:
    int command(msgpack::object &deserialized) {
        MessageUnit data;
        deserialized.convert(data);

        switch ((Command) data.get_command()) {
            case JOIN:
                std::cout << "JOIN" << std::endl;
//                std::cout << data.get_channelIndex() << std::endl;
                return data.get_channelIndex();
            case LEAVE:
                std::cout << "LEAVE" << std::endl;
                return -1;
            case SEND:
                std::cout << "SEND" << std::endl;
                std::cout << data.get_userName() << ": " << data.get_message() << std::endl;
                return 0;
        }
    }
};

#endif//CHATTING_SERVER_MESSAGE_H
