#include "message.h"

std::pair<int, int> Message::command(msgpack::object &deserialized) {
    MessageUnit data;
    deserialized.convert(data);

    switch ((Command) data.get_command()) {
        case CREATE:
            return std::make_pair(1, data.get_channelIndex());
        case JOIN:
            std::cout << "JOIN" << std::endl;
//                std::cout << data.get_channelIndex() << std::endl;
            return std::make_pair(1, data.get_channelIndex());
        case LEAVE:
            std::cout << "LEAVE" << std::endl;
            return std::make_pair(2, 0);
        case CHAT:
            std::cout << "SEND" << std::endl;
            std::cout << data.get_userName() << ": " << data.get_message() << std::endl;
            return std::make_pair(3, 0);
        default:
            return std::make_pair(-1, 0);
    }
}


