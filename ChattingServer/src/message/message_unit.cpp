#include "message_unit.h"

int MessageUnit::get_command() const {
    return command;
}

int MessageUnit::get_channelIndex() const {
    return channelIndex;
}

std::string MessageUnit::get_userName() {
    return userName;
}

std::string MessageUnit::get_message() {
    return message;
}