#include "message.h"

std::pair<int, int> Message::command(msgpack::object &deserialized) {
    MessageUnit data;
    deserialized.convert(data);

    switch (static_cast<Command>(data.get_command())) {
        case CREATE:
            std::cout << "CREATE" << std::endl;
            return std::make_pair(1, data.get_channelIndex());
        case JOIN:
            std::cout << "JOIN" << std::endl;
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


