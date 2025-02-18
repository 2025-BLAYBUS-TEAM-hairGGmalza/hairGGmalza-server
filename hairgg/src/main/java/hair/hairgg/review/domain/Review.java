package hair.hairgg.review.domain;

import hair.hairgg.designer.domain.Designer;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "designerId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Designer designer;

    @Column(nullable = false, length = 1000)
    private String review;

    @Column(nullable = false)
    private Integer score;

    @OneToMany(mappedBy = "review", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    List<ReviewImage> reviewImages = new ArrayList<>();
}
