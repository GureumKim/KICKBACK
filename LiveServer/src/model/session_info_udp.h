//
// Created by bullie on 24. 5. 1.
// 세션 정보, endpoint와 채널번호를 가짐
//

#ifndef SESSION_INFO_UDP_H
#define SESSION_INFO_UDP_H

#include <boost/asio.hpp>

class SessionInfoUDP {
public:
    SessionInfoUDP() {}

    SessionInfoUDP(const boost::asio::ip::udp::endpoint &endpoint, const int channel_number = 256) {
        this->endpoint_ = endpoint;
        this->channel_number_ = channel_number;
    }

    // 채널 번호 설정
    void setChannelNumber(const int channel_number) {
        this->channel_number_ = channel_number;
    }

    // 채널 번호 리턴
    int getChannelNumber() const {
        return channel_number_;
    }

private:
    boost::asio::ip::udp::endpoint endpoint_;
    int channel_number_;
};

#endif //SESSION_INFO_UDP_H
