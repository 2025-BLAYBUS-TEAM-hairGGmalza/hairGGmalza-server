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
	@OneToMany(mappedBy = "designer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Builder.Default
	private List<DesignerMajor> designerMajors = new ArrayList<>();

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
