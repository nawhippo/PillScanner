package Nate.PillScanner.Dispense;

import Nate.PillScanner.Nurse.Nurse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;

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
    private Long camperId;

    @Column
    private String meal;

    @Column
    private Long nurseId;

    @Column
    private Long quantity;

    @Column
    private boolean dispensed;

    @Column
    private boolean missed;

    @Column
    private String time;
}