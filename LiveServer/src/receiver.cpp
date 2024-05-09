//
// Created by SSAFY on 2024-04-30.
//

#include <iostream>
#include <string>

#include "model/connection_info_udp.h"
#include "model/message_form.h"
#include "util/thread_safe_channel.h"
#include "util/thread_safe_queue.h"

using boost::asio::ip::udp;

// 상수
enum {
    buffer_max_length = 1024
};

class Receiver {
public:
    void run() {
        std::cout << "receiver run!" << std::endl;
        do_receive();
    }

private:
    void do_receive() {
        ConnectionInfoUDP::getInstance().socket().async_receive_from(
                boost::asio::buffer(receive_buffer_, buffer_max_length), remote_endpoint,
                [this](boost::system::error_code ec, std::size_t bytes_recvd) {
                    if (!ec && bytes_recvd > 0) {
                        //                        std::string received_message(receive_buffer_, bytes_recvd);

                        MessageForm message_form(receive_buffer_, bytes_recvd);
//                        std::cout << "command: " << message_form.getCommand() << " / channel number: " << message_form.getChannelNumber() << " / message: " << message_form.getMessage() << std::endl;

                        // mutex lock 후 message_queue에 넣기
                        ThreadSafeQueue::getInstance().push(remote_endpoint, message_form);
                        do_receive();
                    }
                });
    }

    udp::endpoint remote_endpoint;
    char receive_buffer_[buffer_max_length]{};
};