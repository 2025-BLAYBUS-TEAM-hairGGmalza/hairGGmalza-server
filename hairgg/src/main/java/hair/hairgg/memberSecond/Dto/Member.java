package hair.hairgg.memberSecond.Dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="memberId")
    private Long id;

    @Column(unique = true, nullable = false)
    private String loginId;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String gender;
    private String profileUrl;

    public Member(String loginId, String name, String profileUrl, String nickname, String gender, String phoneNumber) {
        this.loginId = loginId;
        this.name = name;
        this.profileUrl = profileUrl;
        this.nickname = nickname;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }
}
