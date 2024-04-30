#include <set>
#include <boost/asio.hpp>

using boost::asio::ip::udp;

class client_manager {
public:
    // 싱글톤 인스턴스를 반환하는 메서드
    static client_manager &get_instance() {
        static client_manager instance;
        return instance;
    }

    // 클라이언트 추가 메서드
    void add_client(udp::endpoint endpoint) {
        std::lock_guard<std::mutex> lock(mutex_);   // 스레드 안전을 위한 락
        clients.insert(endpoint);
    }

    // 클라이언트 목록을 반환하는 메서드
    std::set<udp::endpoint> get_clients() const {
        return clients;
    }

    void print_client_list() {
        std::ostringstream oss;
        for (auto it = clients.begin(); it != clients.end(); ++it) {
            if (it != clients.begin()) {
                oss << ", ";
            } else {
                oss << "[";

            }

            oss << it->address().to_string() << ":" << it->port();
        }

        std::cout << "[UDP]Client list: " << oss.str() << "]" << std::endl;
    }

    // 복사 및 할당을 방지
    client_manager(const client_manager &) = delete;

    client_manager &operator=(const client_manager &) = delete;

private:
    // 기본 생성자를 private으로 설정해 외부에서의 인스턴스화 방지
    client_manager() {}

    std::set<udp::endpoint> clients;    // 클라이언트 저장 컨테이너
    mutable std::mutex mutex_;  // 멤버 함수 스레드 안전을 위한 뮤텍스
};
