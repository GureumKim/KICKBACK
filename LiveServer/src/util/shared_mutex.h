//
// Created by SSAFY on 2024-04-30.
// 싱글톤 패턴 사용하여 mutex와 condition_variable 한 개로 공유하기
//

#ifndef LIVESERVER_SHARED_MUTEX_H
#define LIVESERVER_SHARED_MUTEX_H

#include <mutex>

class SharedMutex {
public:
    static SharedMutex &getInstance() {
        static SharedMutex instance;
        return instance;
    }

    std::mutex &getMutex() {
        return mutex_;
    }

    std::condition_variable &getConditionVariable() {
        return condition_variable_;
    }

private:
    // 기본생성자 사용
    SharedMutex() = default;

    // 객체는 유일하게 하나만 생성해야 함
    // 따라서 복사(대입), 이동(대입) 생성자 delete 해서
    // 실수로 복사, 이동생성자 호출 막기
    SharedMutex(const SharedMutex &) = delete;
    SharedMutex &operator=(const SharedMutex &) = delete;
    SharedMutex(SharedMutex &&) = delete;
    SharedMutex &operator=(SharedMutex &&) = delete;

    mutable std::mutex mutex_;
    std::condition_variable condition_variable_;
};

#endif//LIVESERVER_SHARED_MUTEX_H
