#include "message.h"

std::pair<int, int> Message::command(std::shared_ptr<ChatSession>& session, msgpack::object &deserialized) {
    MessageUnit data;
    deserialized.convert(data);

    switch ((Command) data.get_command()) {
        case CREATE:
            session->move_to_channel(data.get_channelIndex());
            return std::make_pair(1, 2);
        case JOIN:
            std::cout << "JOIN" << std::endl;
//                std::cout << data.get_channelIndex() << std::endl;
            return 1;
        case LEAVE:
            std::cout << "LEAVE" << std::endl;
            return 2;
        case CHAT:
            std::cout << "SEND" << std::endl;
            std::cout << data.get_userName() << ": " << data.get_message() << std::endl;
            return 3;
        default:
            return -1;
    }
}


