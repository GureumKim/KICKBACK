#include <msgpack.hpp>

class packet
{
public:
    static msgpack::sbuffer packing(const std::string& message)
    {
        msgpack::sbuffer sbuf;
        msgpack::pack(sbuf, message);

        return sbuf;
    }

    static std::string unpacking(const msgpack::sbuffer& data) {
        msgpack::unpacked result;
        msgpack::unpack(result, data.data(), data.size());
        msgpack::object obj = result.get();

        std::string message;
        obj.convert(message);
        return message;
    }
};