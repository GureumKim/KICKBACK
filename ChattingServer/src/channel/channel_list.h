#ifndef CHATTINGSERVER_CHANNEL_LIST_H
#define CHATTINGSERVER_CHANNEL_LIST_H

#include <memory>
#include <unordered_map>

#include "channel.h"

class ChannelList {
public:
    static ChannelList &get_instance();

    ChannelList(const ChannelList &) = delete;
    ChannelList &operator=(const ChannelList &) = delete;

    void add_channel(const int index);

    // 채널 객체의 참조를 반환합니다.
    Channel& get_channel(const int index);

    bool remove_channel(const int index);

private:
    ChannelList() {
        add_channel(0); // 기본 채널 추가
    }

    // channel 객체를 관리하기 위해 unique_ptr을 사용합니다.
    std::unordered_map<int, std::unique_ptr<Channel>> channels_;
};


#endif //CHATTINGSERVER_CHANNEL_LIST_H
