//
// Created by SSAFY on 2024-05-02.
// message pack
//

#ifndef LIVESERVER_MESSAGE_FORM_H
#define LIVESERVER_MESSAGE_FORM_H

#include "session_info_udp.h"
#include "msgpack.hpp"

class MessageForm {
public:
    MSGPACK_DEFINE(message_);

    MessageForm() {}
    MessageForm(const std::string &message) {
        this->message_ = message;
    }
    // sbuf 언패킹
    MessageForm(const msgpack::sbuffer &sbuf) {
        msgpack::object_handle oh = msgpack::unpack(sbuf.data(), sbuf.size());
        msgpack::object obj = oh.get();
        obj.convert(*this);
    }
    // char배열, size_t 받아 언패킹
    MessageForm(const char* data, size_t size) {
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

    std::string getMessage() {
        return message_;
    }

    void setMessage(const std::string &message) {
        this->message_ = message;
    }

private:
    std::string message_;

};

#endif //LIVESERVER_MESSAGE_FORM_H
