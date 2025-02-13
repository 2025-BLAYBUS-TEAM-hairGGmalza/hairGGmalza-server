package hair.hairgg.reservation;

import java.time.LocalDateTime;

import hair.hairgg.designer.domain.Designer;
import hair.hairgg.designer.domain.MeetingType;
import hair.hairgg.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Builder
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
    private int price;
    @Column
    private LocalDateTime paymentAt;
    @Column
    private PaymentMethod paymentMethod;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designerId")
    private Designer designer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

}
