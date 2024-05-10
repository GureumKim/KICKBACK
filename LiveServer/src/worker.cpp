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

class Worker {
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
        std::cout << "==========================================================================================\n";
//        std::cout << "Processing message(" << sender << "): " << message_form.getCommand() << std::endl;
        std::cout << "command: " << message_form.getCommand() << " / channel number: " << message_form.getChannelNumber() << std::endl;
        float x, y, z, rw, rx, ry, rz;
        message_form.getXYZ(x, y, z);
        message_form.getRWXYZ(rw, rx, ry, rz);
        std::cout << "xyz: " << x << ", " << y << ", " << z << std::endl;
        std::cout << "rwxyz: " << rw << ", " << rx << ", " << ry << ", " << rz << std::endl;

        try {
            switch (message_form.getCommand()) {
                    // 받은 채널 넘버로 채널 생성하기
                case (Command::START): {
                    // 받은 채널 넘버로 채널 생성
                    ThreadSafeChannel::getInstance().makeInitChannel(message_form.getChannelNumber());

                    // 생성한 채널 번호 송신
                    ConnectionInfoUDP::getInstance().socket().send_to(
                            boost::asio::buffer("Channel created number " + std::to_string(message_form.getChannelNumber())), sender);

                    // 테스트출력
                    ThreadSafeChannel::getInstance().printExistChannelNumbers();
                    break;
                }
                    // 받은 채널 넘버에 요청한 엔드포인트 추가
                case (Command::JOIN): {
                    // 받은 채널 번호
                    int recv_channel_number = message_form.getChannelNumber();

                    // 해당 채널 번호 체크
                    if (recv_channel_number < 0 || recv_channel_number > channel_number_max) {
                        ConnectionInfoUDP::getInstance().socket().send_to(
                                boost::asio::buffer("You are trying to send to the wrong channel number: " + std::to_string(recv_channel_number)), sender);
                    }

                    // 받은 채널 넘버에 요청한 엔드포인트 추가
                    ThreadSafeChannel::getInstance().insertUser(recv_channel_number, sender);

                    // 테스트 출력
                    ThreadSafeChannel::getInstance().printExistChannelNumbers();
                    ThreadSafeChannel::getInstance().printChannelUsers(recv_channel_number);
                    break;
                }
                case (Command::END): {
                    // 받은 채널 넘버로 채널 제거
                    ThreadSafeChannel::getInstance().deleteChannel(message_form.getChannelNumber());

                    // 제거한 채널 번호 송신
                    ConnectionInfoUDP::getInstance().socket().send_to(
                            boost::asio::buffer("Deleted created number " + std::to_string(message_form.getChannelNumber())), sender);

                    // 테스트출력
                    ThreadSafeChannel::getInstance().printExistChannelNumbers();

                    break;
                }
                    //                case (Command::MESSAGE): {
                    //                    int recv_channel_number = message_form.getChannelNumber();
                    //                    if (recv_channel_number < 0 || recv_channel_number > channel_number_max) {
                    //                        ConnectionInfoUDP::getInstance().socket().send_to(
                    //                                boost::asio::buffer("You are trying to send to the wrong channel number: " + std::to_string(recv_channel_number)), sender);
                    //                    }
                    //
                    //                    for (const auto &participant: ThreadSafeChannel::getInstance().getUserSet(recv_channel_number)) {
                    //                        if (participant != sender) {
                    //                            ConnectionInfoUDP::getInstance().socket().send_to(boost::asio::buffer(message_form.getMessage()), participant);
                    //                        }
                    //                    }
                    //                    break;
                    //                }
                default: {
                    ConnectionInfoUDP::getInstance().socket().send_to(boost::asio::buffer("Incorrect message"), sender);
                    break;
                }
            }

            std::cout << "==========================================================================================\n";
        } catch (...) {
            ConnectionInfoUDP::getInstance().socket().send_to(boost::asio::buffer("error!"), sender);
        }
    }
};