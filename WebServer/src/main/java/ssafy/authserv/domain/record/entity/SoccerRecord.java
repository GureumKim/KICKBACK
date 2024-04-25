package ssafy.authserv.domain.record.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ietf.jgss.GSSName;
import ssafy.authserv.domain.member.entity.Member;

@Entity
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SoccerRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long id;

    // 이거 오류 생기려나...?
    // 조회 시 생길 수도
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    Member member;

    @Column(nullable = false)
    private int wins = 0;

}
