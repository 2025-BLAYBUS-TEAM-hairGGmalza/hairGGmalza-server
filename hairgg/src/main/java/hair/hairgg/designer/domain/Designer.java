package hair.hairgg.designer.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    @Enumerated(EnumType.ORDINAL)
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
    @Enumerated(EnumType.ORDINAL)
    private MeetingType meetingType;

    private String portfolio1;
    private String portfolio2;

    @Column(nullable = false)
    private int favoriteCount = 0;

    @OneToMany(mappedBy = "designer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<DesignerMajor> designerMajors = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.favoriteCount = 0;
    }

    public void addFavorite() {
        this.favoriteCount++;
    }

    public void deleteFavorite() {
        this.favoriteCount--;
    }

    public void addDesignerMajor(DesignerMajor designerMajor) {
        this.designerMajors.add(designerMajor);
        designerMajor.updateDesigner(this);
    }

    public int getPriceByMeetingType(MeetingType meetingType) {
        if (meetingType == MeetingType.OFFLINE) {
            return offlinePrice;
        }
        if (meetingType == MeetingType.ONLINE) {
            return onlinePrice;
        }
        throw new IllegalArgumentException("MeetingType이 올바르지 않습니다.");
    }
}
