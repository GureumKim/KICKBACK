#ifndef CHATTING_SERVER_MESSAGE_H
#define CHATTING_SERVER_MESSAGE_H

#include <iostream>
#include "channel_list.cpp"
#include "../include/msgpack.hpp"

class MessageUnit {
public:
    MSGPACK_DEFINE(command, channelIndex, userName, message);

    int get_command() const {
        return command;
    }

    int get_channelIndex() const {
        return channelIndex;
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
    void command(msgpack::object &deserialized) {
        MessageUnit data;
        deserialized.convert(data);

        switch ((Command) data.get_command()) {
            case JOIN:
                std::cout << "JOIN" << std::endl;
                break;
            case LEAVE:
                std::cout << "LEAVE" << std::endl;
                break;
            case SEND:
                std::cout << "SEND" << std::endl;
                std::cout << data.get_message() << std::endl;
                send(data);
                break;
        }
    }

private:
    void send(MessageUnit data) {
        msgpack::sbuffer buffer;
        msgpack::packer<msgpack::sbuffer> pk(&buffer);
        data.msgpack_pack(pk);

        std::vector<char> serializedData(buffer.data(), buffer.data() + buffer.size());

        std::cout << 1 << std::endl;
        channel_list::get_instance().get_channel(data.get_channelIndex()).deliver(serializedData);
        std::cout << 4 << std::endl;
    }
};

#endif//CHATTING_SERVER_MESSAGE_H