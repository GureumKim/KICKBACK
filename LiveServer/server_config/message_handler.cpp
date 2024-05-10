#include <iostream>
#include <msgpack.hpp>

#include "message_handler.h"
#include "../src/model/message_form.h"
#include "../src/util/thread_safe_queue.h"

void MessageHandler::command(msgpack::object &deserialized) {
    MessageUnit data_{};
    deserialized.convert(data_);

    switch ((Command) data_.get_command()) {
        case LIVESERVER:
            break;
        case CLIENT:
            break;
        case CREATE:
            break;
        case JOIN:
            break;
        case LEAVE:
            break;
        case READY:
            break;
        case START: {
            std::cout << "Command: START" << std::endl;
            std::cout << "Channel: " << data_.get_channel_index() << std::endl;

            // localhost:0 엔드포인트(빈 임시 엔드포인트) 만들어서 MessageForm 만들고 메시지 큐에 넣기
            boost::asio::ip::udp::endpoint endpoint(boost::asio::ip::address_v4::loopback(), 0);
            MessageForm message_form(data_.get_command(), data_.get_channel_index());
            ThreadSafeQueue::getInstance().push(endpoint, message_form);
            break;
        }
        case ITEM:
            break;
        case END:
            std::cout << "Command: END" << std::endl;
            std::cout << "Channel: " << data_.get_channel_index() << std::endl;
            break;
    }
}
