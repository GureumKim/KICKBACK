package ssafy.authserv.domain.friendship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ssafy.authserv.domain.friendship.entity.Friendship;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    @Query("SELECT f FROM Friendship f WHERE f.receiver.id = :receiverId AND f.requester.id = :requesterId")
    Optional<Friendship> findByReceiverIdAndRequesterId(@Param("receiverId") UUID receiverId, @Param("requesterId") UUID requesterId);

}
