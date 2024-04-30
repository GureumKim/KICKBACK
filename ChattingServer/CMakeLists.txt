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

