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

include_directories(${Boost_INCLUDE_DIRS} ${PROJECT_SOURCE_DIR}/include)

# 대상 추가
add_executable(${PROJECT_NAME}
        udp_echo_client.cpp
        udp_multi_thread_server.cpp
        msgpack_test.cpp
        src/live_server.cpp
        src/model/connection_info_udp.cpp
        src/util/shared_mutex.h
        src/util/thread_safe_queue.h
        src/util/thread_safe_channel.h
)
target_link_libraries(${PROJECT_NAME} ${Boost_LIBRARIES})

# 각각 실행
add_executable(udp_echo_client udp_echo_client.cpp)
target_include_directories(udp_echo_client PRIVATE ${PROJECT_SOURCE_DIR}/include)

add_executable(udp_multi_thread_server udp_multi_thread_server.cpp)
target_include_directories(udp_multi_thread_server PRIVATE ${PROJECT_SOURCE_DIR}/include)

add_executable(msgpack_test msgpack_test.cpp)
target_include_directories(msgpack_test PRIVATE ${PROJECT_SOURCE_DIR}/include)

add_executable(src_live_server src/live_server.cpp
        src/receiver.cpp
        src/worker.cpp)
target_include_directories(src_live_server PRIVATE ${PROJECT_SOURCE_DIR}/include)