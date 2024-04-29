#include "../include/msgpack.hpp"

class CUnit {
private:
    int command;
    std::string message;

public:
    MSGPACK_DEFINE(command, message);

    int get_command() {
        return command;
    }

    std::string get_message() {
        return message;
    }
};

class message_handler {
    enum Command {
        CREATE, END
    };

public:
    static void command(msgpack::object &deserialized) {
        CUnit data_;
        deserialized.convert(data_);

        switch ((Command) data_.get_command()) {
            case CREATE:
                std::cout << "CREATE" << std::endl;
                break;
            case END:
                std::cout << "END: " << data_.get_message() << std::endl;
                break;
        }

    }
};