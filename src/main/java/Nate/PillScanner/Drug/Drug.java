package Nate.PillScanner.Drug;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Drug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long mg;

    @Column(unique = true)
    private String name;

    @Column
    private String form;

    @Column
    private String route;

    @Column
    private String description;

    @Column
    private Long supply;

}
