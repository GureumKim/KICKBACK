cmake_minimum_required(VERSION 3.29.2)
project(
        BoostAsioTest
        VERSION 0.1
        DESCRIPTION "Boost.Asio 테스팅"
        LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 17)

# Boost 설정
find_package(Boost)

if(Boost_FOUND)
    include_directories(${Boost_INCLUDE_DIRS})
endif()

# 대상 추가
add_executable(BoostAsioTest
        server/chat_main.cpp
        chat_client.cpp
        upd_echo_server.cpp
        udp_echo_client.cpp
)
target_link_libraries(BoostAsioTest ${Boost_LIBRARIES})

# 각각 실행
add_executable(server_chat_main server/chat_main.cpp)
add_executable(chat_client chat_client.cpp)

add_executable(upd_echo_server upd_echo_server.cpp)
add_executable(udp_echo_client udp_echo_client.cpp)