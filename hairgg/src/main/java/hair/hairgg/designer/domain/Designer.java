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
	private long id;
	@Column(nullable = false, length = 10)
	private String name;
	@Column(nullable = false)
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
	@Column(nullable = false)
	private MeetingType meetingType;

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
