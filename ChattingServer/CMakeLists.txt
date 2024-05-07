# Window
cmake_minimum_required(VERSION 3.29)
project(ChattingServer)

set(CMAKE_CXX_STANDARD 17)

find_package(Boost)

if(Boost_FOUND)
    include_directories(${Boost_INCLUDE_DIRS})
endif()

add_executable(${PROJECT_NAME}
        src/main.cpp
)

# Build
#cmake_minimum_required(VERSION 3.11)
#project(ChattingServer)
#
#set(CMAKE_CXX_STANDARD 17)
#
#find_package(Boost)
#find_package(Threads REQUIRED)
#
#add_executable(${PROJECT_NAME}
#        src/main.cpp
#)
#
#include_directories(${Boost_INCLUDE_DIRS} ${PROJECT_SOURCE_DIR}/include)
#target_link_libraries(${PROJECT_NAME} ${Boost_LIBRARIES} ${CMAKE_THREAD_LIBS_INIT})

# Mac
#cmake_minimum_required(VERSION 3.28.1)
#project(ChattingServer)
#
#set(CMAKE_CXX_STANDARD 17)
#
#find_package(Boost REQUIRED COMPONENTS thread)
#include_directories(${Boost_INCLUDE_DIRS})
#
#find_package(msgpack-cxx REQUIRED)
#
#add_executable(${PROJECT_NAME} src/main.cpp)
#
#target_link_libraries(${PROJECT_NAME} ${Boost_LIBRARIES} Boost::thread)
#target_link_libraries(${PROJECT_NAME} msgpack-cxx)
