package ssafy.authserv.domain.friendship.entity;

import jakarta.persistence.*;
import lombok.*;
import ssafy.authserv.domain.friendship.entity.enums.FriendshipStatus;
import ssafy.authserv.domain.member.entity.Member;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "friendships")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FetchType.LAZY는 @ManyToOne과 @OneToOne 관계에 대해서는 기본 값이 아님(default : FetchType.EAGER)

    // 따라서 필요하지 않은 데이터까지 즉시 로드되지 않도록 (성능을 위해) LAZY 로딩 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", referencedColumnName = "id")
    private Member requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private Member receiver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendshipStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // 송신자 (친구 요청 보낸) 여부
//    @Column(columnDefinition = "TINYINT(1)")
//    private boolean isFrom;
}
