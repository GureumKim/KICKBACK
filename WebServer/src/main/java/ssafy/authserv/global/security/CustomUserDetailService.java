package ssafy.authserv.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
     Member member = memberRepository.findByEmail(email)
             .orElseThrow(() -> new UsernameNotFoundException(("User not found with the email: " + email)));

     return CustomUserDetails.builder()
             .member(member)
             .build();
    }
}
