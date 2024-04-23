//package ssafy.authserv.domain.user.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import ssafy.authserv.domain.user.entity.enums.Role;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.UUID;
//
//@Entity
//@Getter
//@Setter
//public class User implements UserDetails {
//    @Id
//    @GeneratedValue(generator = "UUID")
//    private UUID id;
//
//    private String email;
//    private String nickname;
//    private String profileImage;
//    private String password;
//
//    @Enumerated(EnumType.STRING)
//    private Role role;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
//    }
//
//
//    // UserDetails 인터페이스 구현
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        // 이메일을 사용자의 식별자로 사용
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked(){
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//}
