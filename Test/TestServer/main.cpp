#include <iostream>
#include <string>
#include <boost/thread/thread.hpp>
#include "udp_server.cpp"
#include "tcp_connect.cpp"

int main() {
    try {
        boost::asio::io_context io_context;
        udp_server server(io_context);

        tcp::resolver resolver(io_context);
        auto endpoints = resolver.resolve("localhost", "1370");
        tcp_connect tcp_connect(io_context, endpoints);

        boost::thread server_thread([&io_context]() { io_context.run(); });
        boost::thread client_thread([&io_context]() { io_context.run(); });

        server_thread.join();
        client_thread.join();
    }
    catch (std::exception &e) {
        std::cerr << e.what() << std::endl;
    }

    return 0;
}
