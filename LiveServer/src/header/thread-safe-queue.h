//
// Created by SSAFY on 2024-04-29.
// 스레드 세이프 큐
//

#ifndef LIVESERVER_THREAD_SAFE_QUEUE_H
#define LIVESERVER_THREAD_SAFE_QUEUE_H

#include <iostream>
#include <mutex>
#include <queue>

template<typename T>
class ThreadSafeQueue {
public:
    ThreadSafeQueue() {}

    ThreadSafeQueue(const ThreadSafeQueue &other) {
        std::lock_guard<std::mutex> lock(other.mutex_);
        queue_ = other.queue;
    }

    void push(T value) {
        std::lock_guard<std::mutex> lock(mutex_);
        queue_.push(std::move(value));
        cond_.notify_one();
    }

    void wait_and_pop(T &value) {
        std::unique_lock<std::mutex> lock(mutex_);
        cond_.wait(lock, [this] { return !queue_.empty(); });
        value = std::move(queue_.front());
        queue_.pop();
    }

private:
    std::queue<T> queue_;
    mutable std::mutex mutex_;
    std::condition_variable cond_;
};

#endif //LIVESERVER_THREAD_SAFE_QUEUE_H
