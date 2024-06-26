#cmake_minimum_required(VERSION 3.29.2)
#project(
#        LiveServer
#        VERSION 0.1
#        DESCRIPTION "Boost.Asio 활용한 UDP 게임 서버"
#        LANGUAGES CXX)
#
#set(CMAKE_CXX_STANDARD 17)
#
##set(CMAKE_C_FLAGS "-fexec-charset=CP949")
#
## Boost
#find_package(Boost REQUIRED)
##Thread
#find_package(Threads REQUIRED)
#
## 대상 추가
#add_executable(${PROJECT_NAME}
#        udp_echo_client.cpp
#        src/live_server.cpp
#        src/model/connection_info_udp.h
#        src/model/message_form.h
#        src/util/shared_mutex.h
#        src/util/thread_safe_queue.h
#        src/util/thread_safe_channel.h
#        server_config/message_handler.h
#        server_config/message_handler.cpp
#        server_config/message_unit.h
#        server_config/message_unit.cpp
#        server_config/tcp_connect.cpp
#        src/util/msgpack_util.h
#)
#
## Boost, Msgpack
#include_directories(${Boost_INCLUDE_DIRS} ${PROJECT_SOURCE_DIR}/include)
#
## 대상에 Boost 및 Thread 라이브러리 링크
#target_link_libraries(${PROJECT_NAME} ${Boost_LIBRARIES} ${CMAKE_THREAD_LIBS_INIT})
#
## 각각 실행
#add_executable(udp_echo_client udp_echo_client.cpp)
#target_link_libraries(udp_echo_client ${Boost_LIBRARIES} ${CMAKE_THREAD_LIBS_INIT})
#
#add_executable(src_live_server src/live_server.cpp
#        src/receiver.cpp
#        src/worker.cpp
#        server_config/tcp_connect.cpp
#        src/model/connection_info_udp.h
#        src/model/message_form.h
#        src/util/shared_mutex.h
#        src/util/thread_safe_queue.h
#        src/util/thread_safe_channel.h
#        server_config/message_handler.h
#        server_config/message_handler.cpp
#        server_config/message_unit.h
#        server_config/message_unit.cpp)
#target_link_libraries(src_live_server ${Boost_LIBRARIES} ${CMAKE_THREAD_LIBS_INIT})

# Mac
cmake_minimum_required(VERSION 3.28.1)
project(
        LiveServer
        VERSION 0.1
        DESCRIPTION "Boost.Asio 활용한 UDP 게임 서버"
        LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 17)

# Boost
find_package(Boost REQUIRED)
#Thread
find_package(Threads REQUIRED)

# 대상 추가
add_executable(${PROJECT_NAME}
        udp_echo_client.cpp
        src/live_server.cpp
        src/udp/model/connection_info_udp.h
        src/udp/model/message_form.h
        src/udp/util/shared_mutex.h
        src/udp/util/thread_safe_queue.h
        src/udp/util/thread_safe_channel.h
        src/tcp/message_handler.h
        src/tcp/message_handler.cpp
        src/tcp/message_unit.h
        src/tcp/message_unit.cpp
        src/tcp/tcp_connect.cpp
        src/udp/util/msgpack_util.h
        src/udp/util/thread_safe_channel.cpp
)

# Boost, Msgpack
include_directories(${Boost_INCLUDE_DIRS} ${PROJECT_SOURCE_DIR}/include)

# 대상에 Boost 및 Thread 라이브러리 링크
target_link_libraries(${PROJECT_NAME} ${Boost_LIBRARIES} ${CMAKE_THREAD_LIBS_INIT})

# 각각 실행
add_executable(udp_echo_client udp_echo_client.cpp)
target_link_libraries(udp_echo_client ${Boost_LIBRARIES} ${CMAKE_THREAD_LIBS_INIT})


add_executable(src_live_server src/live_server.cpp
        src/udp/receiver.cpp
        src/udp/worker.cpp
        src/tcp/tcp_connect.cpp
        src/tcp/message_handler.h
        src/tcp/message_handler.cpp
        src/tcp/message_unit.h
        src/tcp/message_unit.cpp
        src/udp/model/connection_info_udp.h
        src/udp/model/message_form.h
        src/udp/util/shared_mutex.h
        src/udp/util/thread_safe_queue.h
        src/udp/util/thread_safe_queue.cpp
        src/udp/util/thread_safe_channel.h
        src/udp/util/thread_safe_channel.cpp)
target_link_libraries(src_live_server ${Boost_LIBRARIES} ${CMAKE_THREAD_LIBS_INIT})