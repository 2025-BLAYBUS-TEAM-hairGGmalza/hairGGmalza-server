package hair.hairgg.member;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@NoArgsConstructor
@Data
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 증가하는 ID
    @Column(name="member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String googleId;
    @Column(unique = true, nullable = false)
    private String loginId;
    @Column(nullable = false)
    private String name;
    private String nickname;
    private String phoneNumber;
    private String gender;
    @Column(nullable = false)
    private String profileUrl;

    public Member(String googleId, String loginId, String name, String profileUrl, String nickname, String gender, String phoneNumber) {
        this.googleId = googleId;
        this.loginId = loginId;
        this.name = name;
        this.profileUrl = profileUrl;
        this.nickname = nickname;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
