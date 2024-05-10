//
// Created by SSAFY on 2024-05-10.
//

#ifndef LIVESERVER_MSGPACK_UTIL_H
#define LIVESERVER_MSGPACK_UTIL_H

#include <msgpack.hpp>

class MsgpackUtil {
public:
    // T타입 객체 pack 해서 msgpack::sbuffer 리턴
    template<typename T>
    static msgpack::sbuffer pack(const T &object) {
        msgpack::sbuffer sbuf;
        msgpack::pack(sbuf, object);

        return sbuf;
    }

    // msgpack::sbuffer를 받아 T타입 객체로 unpack
    template<typename T>
    static T unpack(const msgpack::sbuffer &sbuf) {
        msgpack::object_handle oh = msgpack::unpack(sbuf.data(), sbuf.size());
        msgpack::object obj = oh.get();
        T result;
        obj.convert(result);

        return result;
    }

    // char배열과 size를 받아 T타입의 객체로 unpack
    template<typename T>
    static T unpack(const char *data, size_t size) {
        msgpack::sbuffer sbuf(size);
        sbuf.write(data, size);

        return unpack<T>(sbuf);
    }
};

#endif//LIVESERVER_MSGPACK_UTIL_H
