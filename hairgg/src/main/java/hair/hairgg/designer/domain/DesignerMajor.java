package hair.hairgg.designer.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
@Getter
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
}
