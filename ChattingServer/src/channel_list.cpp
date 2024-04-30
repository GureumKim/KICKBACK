#include <unordered_map>
#include <boost/asio.hpp>
#include "channel.cpp"

class channel_list
{
public:
    static channel_list& get_instance()
    {
        static channel_list instance;
        return instance;
    }

    channel_list(const channel_list&) = delete;
    channel_list& operator=(const channel_list&) = delete;

    void add_channel(const int index, const channel& channel)
    {
        channels_[index] = channel;
    }

    channel get_channel(const int index)
    {
        if(channels_.count(index)) {
            return channels_[index];
        }


    }

    bool remove_channel(const int index)
    {
        return channels_.erase(index) > 0;
    }
private:
    channel_list() {
        add_channel(0, channel());
    }

    std::unordered_map<int, channel> channels_;
};