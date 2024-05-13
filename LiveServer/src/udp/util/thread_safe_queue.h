//
// Created by SSAFY on 2024-04-29.
// 스레드 세이프 큐 - 메시지 큐
//

#ifndef LIVESERVER_THREAD_SAFE_QUEUE_H
#define LIVESERVER_THREAD_SAFE_QUEUE_H

#include <queue>
#include <utility>
#include "boost/asio.hpp"
#include "../model/message_form.h"
#include "shared_mutex.h"

class ThreadSafeQueue {
public:
    static ThreadSafeQueue &getInstance();

    void push(const boost::asio::ip::udp::endpoint &endpoint, const MessageForm &message_form);

    void wait_and_pop(std::pair<boost::asio::ip::udp::endpoint, MessageForm> &value);

private:
    ThreadSafeQueue();
    ThreadSafeQueue(const ThreadSafeQueue &) = delete;
    ThreadSafeQueue &operator=(const ThreadSafeQueue &) = delete;
    ThreadSafeQueue(ThreadSafeQueue &&) = delete;
    ThreadSafeQueue &operator=(ThreadSafeQueue &&) = delete;

    // 메시지 큐
    std::queue<std::pair<boost::asio::ip::udp::endpoint, MessageForm>> queue_;
};

#endif // LIVESERVER_THREAD_SAFE_QUEUE_H
