//
// Created by SSAFY on 2024-04-30.
//
#include <iostream>
#include <set>
#include <string>

#include "model/connection_info_udp.h"
#include "util/thread_safe_channel.h"
#include "util/thread_safe_queue.h"

using boost::asio::ip::udp;

class worker {
public:
    void run() {
        std::cout << "worker run!" << std::endl;
        do_work();
    }

private:
    void do_work() {
        std::thread([&]() {
            while (true) {
                std::pair<SessionInfoUDP, std::string> recv_data;
                ThreadSafeQueue::getInstance().wait_and_pop(recv_data);
                std::cout << "endpoint: " << recv_data.first.getEndpoint() << std::endl;
                std::cout << "channelNumber: " << recv_data.first.getChannelNumber() << std::endl;
                std::cout << "data: " << recv_data.second << std::endl;

                udp::endpoint sender = recv_data.first.getEndpoint();
                int channel_number = recv_data.first.getChannelNumber();
                std::string message = recv_data.second;

                process_message(sender, channel_number, message);
            }
        }).detach();
    }

    void process_message(const udp::endpoint &sender, const int channel_number, const std::string &message) {
        // TODO: 실제 메시지 처리 로직
        std::cout << "Processing message(" << sender << "): " << message << std::endl;

        try {
            // Example message format: "CREATE:a", "JOIN:a", "MSG:a:Hello"
            auto command_delimiter_pos = message.find(':');
//            auto room_delimiter_pos = message.find(':', command_delimiter_pos + 1);
            std::string command = message.substr(0, command_delimiter_pos);
//            int channel_number = std::stoi(
//                    message.substr(command_delimiter_pos + 1, room_delimiter_pos - command_delimiter_pos - 1));
//            std::string content = message.substr(room_delimiter_pos + 1);
//            std::cout << command << ", " << channel_number << ", " << content << std::endl;

            if (command == "CREATE") {
                int temp_channel_number = ThreadSafeChannel::getInstance().insertUser(sender);
                ConnectionInfoUDP::getInstance().socket().send_to(
                        boost::asio::buffer("Your channel number is " + std::to_string(temp_channel_number)), sender);
            } else if (command == "JOIN") {
                ThreadSafeChannel::getInstance().insertUser(channel_number, sender);
                ConnectionInfoUDP::getInstance().socket().send_to(
                        boost::asio::buffer("You are joined channel number " + std::to_string(channel_number)), sender);
            } else if (command == "MSG") {
                std::string content = message.substr(command_delimiter_pos);

                for (const auto &participant: ThreadSafeChannel::getInstance().getUserSet(channel_number)) {
                    if (participant != sender) {
                        ConnectionInfoUDP::getInstance().socket().send_to(boost::asio::buffer(content), participant);
                    }
                }
            } else {
                ConnectionInfoUDP::getInstance().socket().send_to(boost::asio::buffer("Incorrect message"), sender);
            }
        }
        catch (...) {
            ConnectionInfoUDP::getInstance().socket().send_to(boost::asio::buffer("Incorrect message"), sender);
        }
    }
};