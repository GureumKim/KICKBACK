cmake_minimum_required(VERSION 3.29.2)
project(
        LiveServer
        VERSION 0.1
        DESCRIPTION "Boost.Asio 활용한 UDP 게임 서버"
        LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 17)

#set(CMAKE_C_FLAGS "-fexec-charset=CP949")

# Boost 설정
find_package(Boost)

if(Boost_FOUND)
    include_directories(${Boost_INCLUDE_DIRS})
endif()

# 대상 추가
add_executable(LiveServer
        udp_echo_client.cpp
        udp_multi_thread_server.cpp
        msgpack_test.cpp
)
target_link_libraries(LiveServer ${Boost_LIBRARIES})

# 각각 실행
add_executable(udp_echo_client udp_echo_client.cpp)
add_executable(udp_multi_thread_server udp_multi_thread_server.cpp)
add_executable(msgpack_test msgpack_test.cpp)
