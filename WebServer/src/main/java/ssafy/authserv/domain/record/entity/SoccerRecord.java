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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    Member member;

    @Column(nullable = false)
    private int wins = 0;

    @Column(nullable = false)
    private int draws = 0;

    @Column(nullable =false)
    private int loses = 0;

    @Column(nullable = false)
    private int scores = 0;

    @Column(nullable = false)
    private int gd = 0; // Goal Difference(골득실)
}
