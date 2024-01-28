package Nate.PillScanner.Dispense;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dispense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long nurse1;

    @Column
    private Long nurse2;

    @Column
    private Long drugId;

    @Column
    private Date time;

    @Column
    private Long quantity;
}