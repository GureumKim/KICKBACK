//
// Created by SSAFY on 2024-05-02.
// message pack
//

#ifndef LIVESERVER_MESSAGE_FORM_H
#define LIVESERVER_MESSAGE_FORM_H

#include <utility>

#include "../util/thread_safe_channel.h"
#include "msgpack.hpp"
#include "../../server_config/message_handler.h"

class MessageForm {
public:
    MSGPACK_DEFINE(command_, message_, channel_number_);

    MessageForm() : command_(-1), channel_number_(-1) {}
    MessageForm(const int command, int channel_number = -1, std::string message = "")
    : command_(command), message_(std::move(message)), channel_number_(validateChannelNumber(channel_number)){}
    // sbuf 언패킹
    MessageForm(const msgpack::sbuffer &sbuf) {
        msgpack::object_handle oh = msgpack::unpack(sbuf.data(), sbuf.size());
        msgpack::object obj = oh.get();
        obj.convert(*this);
    }
    // char배열, size_t 받아 언패킹
    MessageForm(const char *data, size_t size) {
        msgpack::sbuffer sbuf(size);
        sbuf.write(data, size);
        msgpack::object_handle oh = msgpack::unpack(sbuf.data(), sbuf.size());
        msgpack::object obj = oh.get();
        obj.convert(*this);
    }

    // message_ 패킹 후 sbuffer 리턴
    msgpack::sbuffer packMessage() const {
        msgpack::sbuffer sbuf;
        msgpack::pack(sbuf, *this);
        return sbuf;
    }

    int getCommand() {
        return command_;
    }

    void setCommand(const int command) {
        this->command_ = command;
    }

    std::string getMessage() {
        return message_;
    }

    void setMessage(const std::string &message) {
        this->message_ = message;
    }

    int getChannelNumber() {
        return channel_number_;
    }

    void setChannelNumber(const int channel_number) {
        this->channel_number_ = validateChannelNumber(channel_number);
    }

private:
    int command_;
    std::string message_;
    int channel_number_;

    int validateChannelNumber(int channel_number) {
        // 유효한 채널 번호 범위 확인
        if(channel_number < 0 || channel_number > channel_number_max) {
            return -1;  // 유호하지 않은 채널 번호일 경우 -1 리턴
        }
        return channel_number;
    }
};

#endif//LIVESERVER_MESSAGE_FORM_H
