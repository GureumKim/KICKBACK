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
