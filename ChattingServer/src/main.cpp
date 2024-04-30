#include <iostream>
#include <boost/asio.hpp>
#include "chat_server.cpp"

int main()
{
    try
    {
        boost::asio::io_context io_context;

        tcp::endpoint endpoint(tcp::v4(), 1371);
        chat_server_ptr server(new chat_server(io_context, endpoint));

        io_context.run();
    }
    catch (std::exception& e)
    {
        std::cerr << "Exception: " << e.what() << "\n";
    }

    return 0;
}