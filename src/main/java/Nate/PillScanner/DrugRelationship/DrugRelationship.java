package Nate.PillScanner.DrugRelationship;

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
public class DrugRelationship {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String meal;

    @Column
    Long camperId;

    @Column
    Long drugId;

    @Column
    String time;

    @Column
    Long quantity;
}
