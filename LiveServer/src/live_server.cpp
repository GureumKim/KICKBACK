//
// Created by SSAFY on 2024-04-29.
// 라이브 서버
//

#include "../server_config/tcp_connect.cpp"
#include "udp/receiver.cpp"
#include "udp/worker.cpp"

int main() {
    try {
        // TCP와 UDP 처리를 위한 별도의 io_context 인스턴스 생성
        boost::asio::io_context io_context_tcp;
        boost::asio::io_context io_context_udp;

        // 스레드 풀 생성
        std::size_t thread_pool_size = std::thread::hardware_concurrency() * 2;
        std::vector<std::thread> thread_pool;

        // ==============================TCP==============================
        // TCP연결 설정
        boost::asio::ip::tcp::resolver resolver(io_context_tcp);
         auto endpoints = resolver.resolve("192.168.100.146", "1370");   // 01
        // auto endpoints = resolver.resolve("192.168.100.107", "1370");   // DP
//        auto endpoints = resolver.resolve("localhost", "1370");   // localhost

        // TCP 처리 객체
        tcp_connect tcpConnect(io_context_tcp, endpoints);

        // 비즈니스 서버와 연결될 TCP 클라이언트 단일 스레드 시작
        thread_pool.emplace_back([&io_context_tcp] { io_context_tcp.run(); });

        // ==============================UDP==============================
        // UDP 소켓 설정 초기화
        ConnectionInfoUDP::getInstance().init(io_context_udp, 1234);

        // 수신 및 처리 객체
        Receiver receiver;
        Worker worker;

        // UDP receiver용 단일 스레드 시작
        receiver.run();// receiver가 io_context_udp를 사용하도록 수정해야 함
        thread_pool.emplace_back([&io_context_udp] {
            io_context_udp.run();
        });

        // worker용 멀티 스레드 시작
        worker.run();
        for (std::size_t i = 2; i < thread_pool_size; i++) {
            thread_pool.emplace_back([&io_context_udp] { io_context_udp.run(); });
        }

        // 모든 스레드 대기
        for (auto &t: thread_pool) { t.join(); }
    } catch (std::exception &e) {
        std::cerr << e.what() << std::endl;
    }

    return 0;
}