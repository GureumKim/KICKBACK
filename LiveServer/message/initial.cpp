#include <msgpack.hpp>

class Initial {
public:
    int command;
    std::string userName;
    std::string last;

    MSGPACK_DEFINE(command, userName, last);
};