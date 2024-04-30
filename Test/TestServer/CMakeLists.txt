cmake_minimum_required(VERSION 3.29)
project(TestServer)

set(CMAKE_CXX_STANDARD 17)

find_package(Boost REQUIRED COMPONENTS thread)

include_directories(C:/msgpack/include)

if (Boost_FOUND)
    include_directories(${Boost_INCLUDE_DIRS})
endif ()

add_executable(TestServer main.cpp)
target_link_libraries(${PROJECT_NAME} ${Boost_LIBRARIES})





