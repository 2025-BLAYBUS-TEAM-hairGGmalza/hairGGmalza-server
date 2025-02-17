package hair.hairgg.review.domain;

import hair.hairgg.reservation.domain.Reservation;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reservationId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Reservation reservation;

    @Column(nullable = false)
    private String review;

    @Column(nullable = false)
    private Integer score;

    @OneToMany(mappedBy = "review", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    List<ReviewImage> reviewImages = new ArrayList<>();
}
