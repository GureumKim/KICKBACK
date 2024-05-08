//
// Created by SSAFY on 2024-04-30.
//
#include <iostream>
#include <set>
#include <string>

#include "model/connection_info_udp.h"
#include "model/message_form.h"
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
                std::pair<udp::endpoint, MessageForm> recv_data;
                ThreadSafeQueue::getInstance().wait_and_pop(recv_data);

                process_message(recv_data.first, recv_data.second);
            }
        }).detach();
    }

    void process_message(const udp::endpoint &sender, MessageForm &message_form) {
        // TODO: 실제 메시지 처리 로직
        std::cout << "Processing message(" << sender << "): " << message_form.getCommand() << std::endl;

        try {
            switch (message_form.getCommand()) {
                case (Command::CREATE): {
                    int temp_channel_number = ThreadSafeChannel::getInstance().makeInitChannel();

                    ConnectionInfoUDP::getInstance().socket().send_to(
                            boost::asio::buffer("Your channel number is " + std::to_string(temp_channel_number)), sender);
                    break;
                }
                case (Command::JOIN): {
                    int recv_channel_number = message_form.getChannelNumber();
                    ThreadSafeChannel::getInstance().printChannel(recv_channel_number);

                    std::cout << "channel number: " << recv_channel_number;
                    if (recv_channel_number < 0 || recv_channel_number > channel_number_max) {
                        ConnectionInfoUDP::getInstance().socket().send_to(
                                boost::asio::buffer("You are trying to send to the wrong channel number: " + std::to_string(recv_channel_number)), sender);
                    }

                    ThreadSafeChannel::getInstance().insertUser(recv_channel_number, sender);
                    //                    ConnectionInfoUDP::getInstance().socket().send_to(
                    //                            boost::asio::buffer("You are joined channel number " + std::to_string(recv_channel_number)), sender);
                    break;
                }
                case (Command::START): {
                    ConnectionInfoUDP::getInstance().socket().send_to(boost::asio::buffer("START"), sender);

                    break;
                }
                case (Command::MESSAGE): {
                    int recv_channel_number = message_form.getChannelNumber();
                    if (recv_channel_number < 0 || recv_channel_number > channel_number_max) {
                        ConnectionInfoUDP::getInstance().socket().send_to(
                                boost::asio::buffer("You are trying to send to the wrong channel number: " + std::to_string(recv_channel_number)), sender);
                    }

                    for (const auto &participant: ThreadSafeChannel::getInstance().getUserSet(recv_channel_number)) {
                        if (participant != sender) {
                            ConnectionInfoUDP::getInstance().socket().send_to(boost::asio::buffer(message_form.getMessage()), participant);
                        }
                    }
                    break;
                }
                default: {
                    ConnectionInfoUDP::getInstance().socket().send_to(boost::asio::buffer("Incorrect message"), sender);
                    break;
                }
            }
        } catch (...) {
            ConnectionInfoUDP::getInstance().socket().send_to(boost::asio::buffer("Incorrect message"), sender);
        }
    }
};