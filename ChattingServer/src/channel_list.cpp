#include <memory>
#include <unordered_map>

class channel_list {
public:
    static channel_list &get_instance() {
        static channel_list instance;
        return instance;
    }

    channel_list(const channel_list &) = delete;
    channel_list &operator=(const channel_list &) = delete;

    void add_channel(const int index) {
        // 채널을 생성하고 관리하기 위해 unique_ptr을 사용합니다.
        channels_[index] = std::make_unique<channel>();
    }

    // 채널 객체의 참조를 반환합니다.
    channel& get_channel(const int index) {
        if (channels_.count(index) == 0) {
            add_channel(index);
        }
        return *channels_[index];
    }

    bool remove_channel(const int index) {
        return channels_.erase(index) > 0;
    }

private:
    channel_list() {
        add_channel(0); // 기본 채널 추가
    }

    // channel 객체를 관리하기 위해 unique_ptr을 사용합니다.
    std::unordered_map<int, std::unique_ptr<channel>> channels_;
};
