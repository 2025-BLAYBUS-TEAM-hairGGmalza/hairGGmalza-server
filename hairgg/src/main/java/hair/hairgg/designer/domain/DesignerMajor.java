package hair.hairgg.designer.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DesignerMajor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "designerMajorId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designerId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Designer designer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "majorId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Major major;

    public void updateDesigner(Designer designer) {
        this.designer = designer;
    }
}
