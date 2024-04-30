//
// Created by SSAFY on 2024-04-29.
//

#include <boost/asio.hpp>
#include <memory>

using boost::asio::ip::udp;

class ConnectionInfoUDP {
public:
    static ConnectionInfoUDP& instance() {
        static ConnectionInfoUDP instance;
        return instance;
    }

    udp::socket& socket() {
        return *socket_;
    }

    void init(boost::asio::io_context& io_context, short port) {
        socket_ = std::make_unique<udp::socket>(io_context, udp::endpoint(udp::v4(), port));
    }

private:
    std::unique_ptr<udp::socket> socket_;

    // 싱글턴 패턴을 위해 생성자와 복사 생성자를 private로 설정
    ConnectionInfoUDP() {}
    ConnectionInfoUDP(const ConnectionInfoUDP&) = delete;
    ConnectionInfoUDP& operator=(const ConnectionInfoUDP&) = delete;
};
