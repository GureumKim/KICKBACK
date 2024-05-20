package ssafy.authserv.global.jwt.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ssafy.authserv.global.jwt.entity.BlockedAccessToken;

import java.util.List;


@Repository
public interface BlockedAccessTokenRepository extends JpaRepository<BlockedAccessToken, Long> {
    void deleteByExpirationTimeBefore(long expirationTime);

    @Modifying
    @Transactional
    @Query("DELETE FROM BlockedAccessToken b WHERE b.expirationTime < :expirationTime AND b.email = :email")
    void deleteExpiredTokensByEmail(@Param("expirationTime") long expirationTime, @Param("email") String email);
    // Set 말고 JPA 표준 반환 타입인 'List' 타입을 사용, 리팩토링 고려 가능
    List<BlockedAccessToken> findAllByEmail(String email);
}
