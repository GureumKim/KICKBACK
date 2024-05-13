//
// Created by SSAFY on 2024-05-13.
//

#include "thread_safe_queue.h"

ThreadSafeQueue &ThreadSafeQueue::getInstance() {
    static ThreadSafeQueue instance;
    return instance;
}

// 큐에 메시지 넣기
void ThreadSafeQueue::push(const boost::asio::ip::udp::endpoint &endpoint, const MessageForm &message_form) {
    std::lock_guard<std::mutex> lock(SharedMutex::getInstance().getMutex());
    queue_.emplace(endpoint, message_form);
    SharedMutex::getInstance().getConditionVariable().notify_one();
}

// 큐에서 메시지 꺼내기
void ThreadSafeQueue::wait_and_pop(std::pair<boost::asio::ip::udp::endpoint, MessageForm> &value) {
    // 해당 함수에서 조건 변수 사용하기 때문에 unique_lock 사용
    // 큐가 비어있으면 대기(wait) 하다가 큐에 값이 들어오면 꺼냄
    // 여기서, wait 호출 시 자동으로 mutex 잠금 해제 하고 조건 충족 시 다시 mutex lock
    std::unique_lock<std::mutex> lock(SharedMutex::getInstance().getMutex());
    SharedMutex::getInstance().getConditionVariable().wait(lock, [this] { return !queue_.empty(); });
    value = std::move(queue_.front());
    queue_.pop();
}

ThreadSafeQueue::ThreadSafeQueue() = default;
