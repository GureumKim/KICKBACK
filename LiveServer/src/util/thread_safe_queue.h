//
// Created by SSAFY on 2024-04-29.
// 스레드 세이프 큐 - 메시지 큐
//

#ifndef LIVESERVER_THREAD_SAFE_QUEUE_H
#define LIVESERVER_THREAD_SAFE_QUEUE_H

#include <queue>

#include "shared_mutex.h"

class ThreadSafeQueue {
public:
    static ThreadSafeQueue &getInstance() {
        static ThreadSafeQueue instance;
        return instance;
    }

    // 큐에 메시지 넣기
    void push(std::pair<udp::endpoint, std::string> value) {
        std::lock_guard<std::mutex> lock(SharedMutex::getInstance().getMutex());
        queue_.push(std::move(value));
        SharedMutex::getInstance().getConditionVariable().notify_one();
    }

    // 큐에서 메시지 꺼내기
    void wait_and_pop(std::pair<udp::endpoint, std::string> &value) {
        // 해당 함수에서 조건 변수 사용하기 때문에 unique_lock 사용
        // 큐가 비어있으면 대기(wait) 하다가 큐에 값이 들어오면 꺼냄
        // 여기서, wait 호출 시 자동으로 mutex 잠금 해제 하고 조건 충족 시 다시 mutex lock
        std::unique_lock<std::mutex> lock(SharedMutex::getInstance().getMutex());
        SharedMutex::getInstance().getConditionVariable().wait(lock, [this] { return !queue_.empty(); });
        value = std::move(queue_.front());
        queue_.pop();
    }

private:
    ThreadSafeQueue() = default;
    ThreadSafeQueue(const ThreadSafeQueue &) = delete;
    ThreadSafeQueue &operator=(const ThreadSafeQueue &) = delete;
    ThreadSafeQueue(ThreadSafeQueue &&) = delete;
    ThreadSafeQueue &operator=(ThreadSafeQueue &&) = delete;

    // 메시지 큐
    std::queue<std::pair<udp::endpoint, std::string>> queue_;
};

#endif//LIVESERVER_THREAD_SAFE_QUEUE_H