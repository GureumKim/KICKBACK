//
// Created by SSAFY on 2024-04-26.
//
#include <iostream>
#include <vector>
#include "include/msgpack.hpp"

class CUnit {
public:
    std::string unit_name_;
    std::vector<int> lottoNumbers_he_bought_yesterday_;

    MSGPACK_DEFINE(unit_name_, lottoNumbers_he_bought_yesterday_);
};

int main() {
//    // 직렬화할 데이터
//    std::vector<std::string> original = {"MessagePack", "is", "awesome", "코코!"};
//
//    // 데이터 직렬화
//    msgpack::sbuffer sbuf;
//    msgpack::pack(sbuf, original);
//
//    // 직렬화된 데이터 출력
//    std::cout << "Serialized data size: " << sbuf.size() << " bytes" << std::endl;
//
//    // 역직렬화
//    msgpack::unpacked msg;
//    msgpack::unpack(msg, sbuf.data(), sbuf.size());
//    msgpack::object obj = msg.get();
//
//    // 역직렬화된 데이터 사용
//    std::vector<std::string> deserialized;
//    obj.convert(deserialized);
//
//    // 결과 출력
//    for (const auto &str: deserialized) {
//        std::cout << str << std::endl;
//    }
//
//    std::cout << "=====================================================" << std::endl;
//
//    std::vector<std::string> vec;
//    vec.emplace_back("Hello");
//    vec.emplace_back("니하오");
//
//    msgpack::sbuffer sbf;
//    msgpack::pack(sbf, vec);
//    msgpack::object_handle oh = msgpack::unpack(sbf.data(), sbf.size());
//
//    msgpack::object obj_ = oh.get();
//    std::cout << obj_ << std::endl;

//    std::cout << "=====================================================" << std::endl;

//    msgpack::sbuffer buffer;
//    msgpack::packer<msgpack::sbuffer> pk(&buffer);
//    pk.pack(std::string("hello"));
//    pk.pack(std::string("니하오"));
//
//    msgpack::unpacker pac;
//    pac.reserve_buffer(buffer.size());
//    memcpy(pac.buffer(), buffer.data(), buffer.size());
//    pac.buffer_consumed(buffer.size());

//    std::cout << "=====================================================" << std::endl;

    std::vector<CUnit> unitList;

    CUnit me;
    me.unit_name_ = "Me";
    me.lottoNumbers_he_bought_yesterday_ = { 1, 3, 5 };

    CUnit you;
    you.unit_name_ = "You";
    you.lottoNumbers_he_bought_yesterday_ = { 2, 4, 6 };

    unitList.emplace_back(me);
    unitList.emplace_back(you);

    // pack
    msgpack::sbuffer sbuf;
    msgpack::pack(sbuf, unitList);

    // pack2
//    msgpack::sbuffer sbuf;
//    msgpack::packer<msgpack::sbuffer> pk(&sbuf);
//    me.msgpack_pack(pk);

    // unpack
    msgpack::object_handle oh = msgpack::unpack(sbuf.data(), sbuf.size());

    msgpack::object obj = oh.get();

    std::vector<CUnit> rvec;
    obj.convert(rvec);
    for(const auto& rUnit : rvec) {
        for(int i : rUnit.lottoNumbers_he_bought_yesterday_) {
            std::cout << i << std::endl;
        }
    }

    return 0;
}
