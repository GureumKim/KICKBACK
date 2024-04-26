package ssafy.authserv.domain.record.entity;

import jakarta.persistence.*;
import lombok.*;
import ssafy.authserv.domain.member.entity.Member;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class SpeedRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    Member member;


    @Column(nullable = false)
    private int map;

    @Column(nullable = false)
    private float time = 0;

//    @Column(nullable = false)
//    private int scores = 0;
}
