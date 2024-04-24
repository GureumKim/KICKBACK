#include <algorithm>
#include <iostream>
#include <list>
#include <boost/shared_ptr.hpp>
#include <boost/asio.hpp>
#include "chat_server.cpp"

using boost::asio::ip::tcp;

//----------------------------------------------------------------------

int main(int argc, char* argv[])
{
    try
    {
//        if (argc < 2)
//        {
//            std::cerr << "Usage: chat_server <port> [<port> ...]\n";
//            return 1;
//        }

        boost::asio::io_context io_context;

        chat_server_list servers;

        using namespace std; // For atoi.
        tcp::endpoint endpoint(tcp::v4(), 9999);
        chat_server_ptr server(new chat_server(io_context, endpoint));
        servers.push_back(server);

//        for (int i = 1; i < argc; ++i)
//        {
//            using namespace std; // For atoi.
//            tcp::endpoint endpoint(tcp::v4(), atoi(argv[i]));
//            chat_server_ptr server(new chat_server(io_context, endpoint));
//            servers.push_back(server);
//        }

        io_context.run();
    }
    catch (std::exception& e)
    {
        std::cerr << "Exception: " << e.what() << "\n";
    }

    return 0;
}