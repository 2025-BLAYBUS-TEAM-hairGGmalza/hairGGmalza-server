package hair.hairgg.reservation.domain;

import java.time.LocalDateTime;

import hair.hairgg.designer.domain.Designer;
import hair.hairgg.designer.domain.MeetingType;
import hair.hairgg.exception.ErrorCode;
import hair.hairgg.exception.custom.ReservationError;
import hair.hairgg.member.Member;
import hair.hairgg.reservation.domain.pay.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reservationId")
	private long id;
	@Column(nullable = false)
	private LocalDateTime reservationDate;
	@Column(nullable = false)
	private ReservationState reservationState;
	@Column(nullable = false)
	private MeetingType meetingType;
	@Column(nullable = false)
	private String meetUrl;
	@Column(nullable = false)
	private int price;
	@Column
	private LocalDateTime paymentAt;
	@Column
	private PaymentMethod paymentMethod;
	@Column
	private String tid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "designerId")
	private Designer designer;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberId")
	private Member member;

	@Builder
	public Reservation(LocalDateTime reservationDate, MeetingType meetingType, Designer designer, Member member,
		int price, PaymentMethod paymentMethod) {
		if (reservationDate == null || meetingType == null || designer == null || member == null) {
			throw new ReservationError(ErrorCode.INVALID_INPUT_VALUE);
		}
		this.reservationDate = reservationDate;
		this.meetingType = meetingType;
		this.price = price;
		this.designer = designer;
		this.member = member;
		this.paymentMethod = paymentMethod;
		this.reservationState = ReservationState.WAITING;
		this.meetUrl= "https://meet.google.com/landing";
	}

	public void changeState(ReservationState state) {
		this.reservationState = state;
	}

	public void updatePaymentInfo(LocalDateTime approvedAt) {
		this.paymentAt = approvedAt;
	}

	public void updateTid(String tid) {
		this.tid = tid;
	}

	public void updateMeetUrl(String url) {
		this.meetUrl = url;
	}
}
