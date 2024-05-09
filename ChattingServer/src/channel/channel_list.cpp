#include "channel_list.h"

ChannelList &ChannelList::get_instance() {
    static ChannelList instance;
    return instance;
}

void ChannelList::add_channel(const int index) {
    // 채널을 생성하고 관리하기 위해 unique_ptr을 사용합니다.
    channels_[index] = std::make_unique<Channel>();
}

Channel &ChannelList::get_channel(const int index) {
    // 채널 객체의 참조를 반환합니다.
    if (channels_.count(index) == 0) {
        add_channel(index);
    }
    return *channels_[index];
}

bool ChannelList::remove_channel(const int index) {
    return channels_.erase(index) > 0;
}
