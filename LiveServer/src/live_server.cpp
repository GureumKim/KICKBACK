//
// Created by SSAFY on 2024-04-29.
// 라이브 서버
//

#include <iostream>

#include "receiver.cpp"
#include "../server_config/tcp_connect.cpp"
#include "worker.cpp"

int main() {
    try {
        // IO 컨텍스트 객체 생성 후 소켓 설정 초기화
        boost::asio::io_context io_context;
        ConnectionInfoUDP::getInstance().init(io_context, 1234);

        // 수신 및 처리 객체
        Receiver receiver;
        Worker worker;

        std::size_t thread_pool_size = std::thread::hardware_concurrency() * 2;
        std::vector<std::thread> thread_pool;

        boost::asio::ip::tcp::resolver resolver(io_context);
        auto endpoints = resolver.resolve("192.168.100.107", "1370");
        tcp_connect tcpConnect(io_context, endpoints);

        std::thread tcp_connect([&io_context]() {
            io_context.run();
        });

        tcp_connect.join();

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

    return 0;
}