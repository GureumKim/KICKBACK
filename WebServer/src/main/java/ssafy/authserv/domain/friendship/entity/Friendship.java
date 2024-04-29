package ssafy.authserv.domain.friendship.entity;

import jakarta.persistence.*;
import lombok.*;
import ssafy.authserv.domain.member.entity.Member;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long id;

    // FetchType.LAZY는 @ManyToOne과 @OneToOne 관계에 대해서는 기본 값이 아님(default : FetchType.EAGER)
    // 따라서 필요하지 않은 데이터까지 즉시 로드되지 않도록 (성능을 위해) LAZY 로딩 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private Member friend;

    // 친구 요청 수락 상태
    @Column(columnDefinition = "TINYINT(1)")
    private boolean isWating;

    // 송신자 (친구 요청 보낸) 여부
    @Column(columnDefinition = "TINYINT(1)")
    private boolean isFrom;
}
