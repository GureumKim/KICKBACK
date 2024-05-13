//
// Created by SSAFY on 2024-04-29.
//

#ifndef LIVESERVER_CONNECTION_INFO_UDP_CPP
#define LIVESERVER_CONNECTION_INFO_UDP_CPP

#include <boost/asio.hpp>
#include <memory>

using boost::asio::ip::udp;

class ConnectionInfoUDP {
public:
    static ConnectionInfoUDP &getInstance() {
        static ConnectionInfoUDP instance;
        return instance;
    }

    udp::socket &socket() { return *socket_; }

    void init(boost::asio::io_context &io_context, short port) {
        socket_ = std::make_unique<udp::socket>(io_context, udp::endpoint(udp::v4(), port));
    }

private:
    ConnectionInfoUDP() = default;

    ConnectionInfoUDP(const ConnectionInfoUDP &) = delete;

    ConnectionInfoUDP &operator=(const ConnectionInfoUDP &) = delete;

    ConnectionInfoUDP(ConnectionInfoUDP &&) = delete;

    ConnectionInfoUDP &operator=(ConnectionInfoUDP &&) = delete;

    std::unique_ptr<udp::socket> socket_;
};

#endif//LIVESERVER_CONNECTION_INFO_UDP_CPP