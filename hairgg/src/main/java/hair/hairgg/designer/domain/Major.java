package hair.hairgg.designer.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "majorId")
    private Long id;

    private String name;
}
