#include <iostream>
#include <msgpack.hpp>

#include "message_handler.h"

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
        case START:
            std::cout << "Command: START" << std::endl;
            std::cout << "Channel: " << data_.get_channel_index() << std::endl;
            break;
        case ITEM:
            break;
        case END:
            std::cout << "Command: END" << std::endl;
            std::cout << "Channel: " << data_.get_channel_index() << std::endl;
            break;
    }
}
