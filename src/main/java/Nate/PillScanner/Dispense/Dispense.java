package Nate.PillScanner.Dispense;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    private Long drugId;

    @Column
    private String meal;

    @Column
    private LocalDateTime dispenseTime;

    @Column
    private Long quantity;

    @Column
    private boolean dispensed;

    @Column
    private boolean missed;
}