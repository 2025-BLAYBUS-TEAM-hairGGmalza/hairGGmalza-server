package hair.hairgg.security.jwt;

import hair.hairgg.memberSecond.Repository.MemberSecondRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberSecondRepository memberSecondRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberSecondRepository.findByLoginId(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
