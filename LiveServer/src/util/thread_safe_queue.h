//
// Created by SSAFY on 2024-04-29.
// 스레드 세이프 큐
//

#ifndef LIVESERVER_THREAD_SAFE_QUEUE_H
#define LIVESERVER_THREAD_SAFE_QUEUE_H

#include <iostream>
#include <queue>

#include "shared_mutex.h"

template<typename T>
class ThreadSafeQueue {
public:
    ThreadSafeQueue() {}

    ThreadSafeQueue(const ThreadSafeQueue &other) {
        std::lock_guard<std::mutex> lock(SharedMutex::getInstance().getMutex());
        queue_ = other.queue;
    }

    void push(T value) {
        std::lock_guard<std::mutex> lock(SharedMutex::getInstance().getMutex());
        queue_.push(std::move(value));
        SharedMutex::getInstance().getConditionVariable().notify_one();
    }

    void wait_and_pop(T &value) {
        // 해당 함수에서 조건 변수 사용하기 때문에 unique_lock 사용
        // 큐가 비어있으면 대기(wait) 하다가 큐에 값이 들어오면 꺼냄
        // 여기서, wait 호출 시 자동으로 mutex 잠금 해제 하고 조건 충족 시 다시 mutex lock
        std::unique_lock<std::mutex> lock(SharedMutex::getInstance().getMutex());
        SharedMutex::getInstance().getConditionVariable().wait(lock, [this] { return !queue_.empty(); });
        value = std::move(queue_.front());
        queue_.pop();
    }

private:
    std::queue<T> queue_;
};

#endif//LIVESERVER_THREAD_SAFE_QUEUE_H
