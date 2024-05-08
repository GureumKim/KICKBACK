//
// Created by SSAFY on 2024-04-29.
// 라이브 서버
//

#include <iostream>

#include "receiver.cpp"
#include "worker.cpp"

int main() {
    try {
        // IO 컨텍스트 객체 생성 후 소켓 설정 초기화
        boost::asio::io_context io_context;
        ConnectionInfoUDP::getInstance().init(io_context, 1234);

        // 수신 및 처리 객체
        receiver receiver;
        worker worker;

        std::size_t thread_pool_size = std::thread::hardware_concurrency() * 2;
        std::vector<std::thread> thread_pool;

        // receiver용 단일 스레드 시작
        receiver.run();
        thread_pool.emplace_back([&io_context] { io_context.run(); });

        // worker용 멀티 스레드 시작
        worker.run();
        for (std::size_t i = 1; i < thread_pool_size; i++) {
            thread_pool.emplace_back([&io_context] { io_context.run(); });
        }

        // 모든 스레드 대기
        for (auto &t: thread_pool) { t.join(); }
    } catch (std::exception &e) {
        std::cerr << e.what() << std::endl;
    }

//    try {
//        // IO 컨텍스트 객체 생성 후 소켓 설정 초기화
//        boost::asio::io_context io_context;
//        ConnectionInfoUDP::getInstance().init(io_context, 1234);
//
//        // 수신 및 처리 객체
//        receiver receiver;
//        worker worker;
//
//        // receiver용 단일 스레드 시작
//        std::thread receiver_thread([&receiver] { receiver.run(); });
//
//        // worker용 스레드 풀 생성 및 실행
//        std::size_t thread_pool_size = std::thread::hardware_concurrency() * 2;
//        std::vector<std::thread> worker_threads;
//        worker.run();
//        for (std::size_t i = 1; i < thread_pool_size; i++) {
//            worker_threads.emplace_back([&io_context] { io_context.run(); });
//        }
//
//        // 모든 스레드 대기
//        receiver_thread.join();
//        for (auto &t: worker_threads) { t.join(); }
//    } catch (std::exception &e) {
//        std::cerr << e.what() << std::endl;
//    }

    return 0;
}