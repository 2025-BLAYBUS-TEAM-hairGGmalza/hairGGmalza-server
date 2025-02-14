package hair.hairgg.designer.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Designer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "designerId")
    private Long id;
    @Column(nullable = false, length = 10)
    private String name;
    @Enumerated(EnumType.STRING)
    private Region region;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String profile;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private int offlinePrice;
    @Column(nullable = false)
    private int onlinePrice;
    @Enumerated(EnumType.STRING)
    private MeetingType meetingType;
}
