package ssafy.authserv.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import ssafy.authserv.domain.friendship.entity.Friendship;
import ssafy.authserv.domain.member.entity.enums.MemberRole;
import ssafy.authserv.domain.record.entity.SoccerRecord;
import ssafy.authserv.domain.record.entity.SpeedRecord;


import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 별도의 UserDetails를 만들어 단일 책임원칙 준수하고 유연성 획득
 * https://chat.openai.com/c/37749fe9-d0e3-4452-b28e-bbe35e765283
 */
@Entity
//@Table(name = "member") // 아직 미리 안만들고 create 했으니께
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member {

    /** 기본 필드 */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING) // Enum 값을 문자열로 저장
    @Column(nullable = false)
    private MemberRole role;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column
    private String profileImage; // 프로필 이미지 URL 혹은 경로를 저장

    /** 친구 관련 필드 */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true) // Member가 삭제될 때 관련된 모든 Friendship을 삭제
    private Set<Friendship> friendships = new HashSet<>();
    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL, orphanRemoval = true) // Member를 참조하는 Friendships를 삭제
    private Set<Friendship> friendFriendships = new HashSet<>();

    /** 레코드(기록) 관련 필드 */
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    SoccerRecord soccerRecord;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<SpeedRecord> speedRecord;


    public void updatePassword(String password) {this.password = password; }
}
