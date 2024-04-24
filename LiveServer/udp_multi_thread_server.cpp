#include <boost/asio.hpp>
#include <iostream>
#include <string>
#include <unordered_map>
#include <vector>
#include <thread>
#include <mutex>
#include <queue>

using boost::asio::ip::udp;

std::mutex queue_mutex;
std::queue<std::string> message_queue;

// 채팅방 관리 위한 구조체
struct ChatRoom {
    std::vector<udp::endpoint> participants;
};

std::unordered_map<std::string, ChatRoom> chat_rooms;

void process_message(boost::asio::io_context& io_context, udp::socket& socket) {
    while(true) {
        std::string message;
        {   // lock_guard 소멸 위해 블록 지정
            std::lock_guard<std::mutex> lock(queue_mutex);
            if(!message_queue.empty()) {
                message = message_queue.front();
                message_queue.pop();
            }
        }

        // 처리할 메시지가 없다면 계속 진행
        if(message.empty()) {
            continue;
        }

        // 메시지 형식 분석(CREATE, JOIN, MESSAGE)
        std::istringstream iss(message);
        std::string command, room_name, msg;
        iss >> command;

        if(command == "CREATE") {
            iss >> room_name;
            chat_rooms[room_name] = ChatRoom();
            std::cout << "Room created: " << room_name << std::endl;
        }
        else if(command == "JOIN") {
            iss >> room_name;
            chat_rooms[room_name] = ChatRoom();
            std::cout << "Room joined: " << room_name << std::endl;
        }
        else if(command == "MESSAGE") {
            iss >> room_name >> msg;

            std::cout << "MSG send(" << room_name << "): " << msg << '\n';

            for(const auto &participant : chat_rooms[room_name].participants) {
//                if(participant != socket.local_endpoint()) {
                    socket.send_to(boost::asio::buffer(msg), participant);
//                }
            }
        }
        else {
//            socket.send_to(boost::asio::buffer("Incorrect message"), socket.endpoint);
        }
    }
}

void server(boost::asio::io_context& io_context, short port) {
    udp::socket socket(io_context, udp::endpoint(udp::v4(), port));
    char data[1024];

    while(true) {
        udp::endpoint sender_endpoint;
        size_t length = socket.receive_from(boost::asio::buffer(data), sender_endpoint);

        // 받은 메시지를 큐에 추가
        {
            std::lock_guard<std::mutex> lock(queue_mutex);
            message_queue.emplace(data, length);
        }
    }
}

int main() {
    try {
        boost::asio::io_context io_context;

        std::thread server_thread([&io_context]() { server(io_context, 12345); });

        udp::socket socket(io_context);

        // 요청 처리 스레드 시작
        std::thread processing_thread([&io_context, &socket]() { process_message(io_context, socket); });

        server_thread.join();
        processing_thread.join();
    }
    catch(std::exception& e) {
        std::cerr << e.what() << std::endl;
    }

    return 0;
}