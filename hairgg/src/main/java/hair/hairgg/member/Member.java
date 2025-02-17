package hair.hairgg.member;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 증가하는 ID
    @Column(name="memberId")
    private Long id;

    @Column(unique = true, nullable = false)
    private String loginId;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String gender;
    private String profileUrl;

}
