#include "../include/msgpack.hpp"

class chat_participant
{
public:
    virtual ~chat_participant() {}
    virtual void deliver(const std::string& msg) = 0;
};

typedef boost::shared_ptr<chat_participant> chat_participant_ptr;
