//
// Created by SSAFY on 2024-05-02.
// message pack
//

#ifndef LIVESERVER_MESSAGE_FORM_H
#define LIVESERVER_MESSAGE_FORM_H

#include <utility>

#include "../../server_config/message_handler.h"
#include "../util/thread_safe_channel.h"
#include "msgpack.hpp"

class MessageForm {
public:
    MSGPACK_DEFINE(command_,
                   channel_number_,
                   user_index_,
                   x_,
                   y_,
                   z_,
                   rw_,
                   rx_,
                   ry_,
                   rz_);

    MessageForm() : command_(-1), channel_number_(-1) {}

    MessageForm(const int command,
                int channel_number = -1,
                int user_index = -1,
                float x = 0.0f,
                float y = 0.0f,
                float z = 0.0f,
                float rw = 0.0f,
                float rx = 0.0f,
                float ry = 0.0f,
                float rz = 0.0f)
        : command_(command),
          channel_number_(validateChannelNumber(channel_number)),
          user_index_(user_index),
          x_(x),
          y_(y),
          z_(z),
          rw_(rw),
          rx_(rx),
          ry_(ry),
          rz_(rz) {}

    //    // sbuf 언패킹
    //    MessageForm(const msgpack::sbuffer &sbuf) {
    //        msgpack::object_handle oh = msgpack::unpack(sbuf.data(), sbuf.size());
    //        msgpack::object obj = oh.get();
    //        obj.convert(*this);
    //    }
    //    // char배열, size_t 받아 언패킹
    //    MessageForm(const char *data, size_t size) {
    //        msgpack::sbuffer sbuf(size);
    //        sbuf.write(data, size);
    //        msgpack::object_handle oh = msgpack::unpack(sbuf.data(), sbuf.size());
    //        msgpack::object obj = oh.get();
    //        obj.convert(*this);
    //    }

    //    // message_ 패킹 후 sbuffer 리턴
    //    msgpack::sbuffer packMessage() const {
    //        msgpack::sbuffer sbuf;
    //        msgpack::pack(sbuf, *this);
    //        return sbuf;
    //    }

    int getCommand() {
        return command_;
    }

    void setCommand(const int command) {
        this->command_ = command;
    }

    int getChannelNumber() {
        return channel_number_;
    }

    void setChannelNumber(const int channel_number) {
        this->channel_number_ = validateChannelNumber(channel_number);
    }

    int getUserIndex() {
        return user_index_;
    }

    void setUserIndex(int user_index) {
        this->user_index_ = user_index;
    }

    void getXYZ(float &x, float &y, float &z) {
        x = this->x_;
        y = this->y_;
        z = this->z_;
    }

    void setXYZ(float x, float y, float z) {
        this->x_ = x;
        this->y_ = y;
        this->z_ = z;
    }

    void getRWXYZ(float &rw, float &rx, float &ry, float &rz) {
        rw = this->rw_;
        rx = this->rx_;
        ry = this->ry_;
        rz = this->rz_;
    }

    void setRWXYZ(float rw, float rx, float ry, float rz) {
        this->rw_ = rw;
        this->rx_ = rx;
        this->ry_ = ry;
        this->rz_ = rz;
    }

private:
    int command_;
    int channel_number_;
    int user_index_;
    float x_;
    float y_;
    float z_;
    float rw_;
    float rx_;
    float ry_;
    float rz_;

    int validateChannelNumber(int channel_number) {
        // 유효한 채널 번호 범위 확인
        if (channel_number < 0 || channel_number > channel_number_max) {
            return -1;// 유호하지 않은 채널 번호일 경우 -1 리턴
        }
        return channel_number;
    }
};

#endif//LIVESERVER_MESSAGE_FORM_H
