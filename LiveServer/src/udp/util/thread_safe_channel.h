//
// Created by SSAFY on 2024-04-30.
// 싱글톤 패턴을 사용하여 방 공유
//

#ifndef LIVESERVER_THREAD_SAFE_CHANNEL_H
#define LIVESERVER_THREAD_SAFE_CHANNEL_H

#include <boost/asio.hpp>
#include <set>
#include <unordered_map>
#include "shared_mutex.h"

enum {
    channel_number_max = 255
};

class ThreadSafeChannel {
public:
    static ThreadSafeChannel &getInstance();

    int makeInitChannel();
    bool makeInitChannel(int channel_number);
    int insertUser(const boost::asio::ip::udp::endpoint &user_endpoint);
    void insertUser(const int channel_number, const boost::asio::ip::udp::endpoint &user_endpoint);
    const std::set<boost::asio::ip::udp::endpoint> &getUserSet(const int channel_number);
    void deleteChannel(const int channel_number);
    void printChannelUsers(const int channel_number);
    void printExistChannelNumbers();

private:
    ThreadSafeChannel();
    ThreadSafeChannel(const ThreadSafeChannel &) = delete;
    ThreadSafeChannel &operator=(const ThreadSafeChannel &) = delete;
    ThreadSafeChannel(ThreadSafeChannel &&) = delete;
    ThreadSafeChannel &operator=(ThreadSafeChannel &&) = delete;

    // 채널 목록(채널번호, 엔드포인트셋)
    std::unordered_map<int, std::set<boost::asio::ip::udp::endpoint>> channels_;
};

#endif// LIVESERVER_THREAD_SAFE_CHANNEL_H