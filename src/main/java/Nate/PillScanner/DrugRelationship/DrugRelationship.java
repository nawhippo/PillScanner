package Nate.PillScanner.DrugRelationship;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


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

    @ElementCollection
    List<String> meals;

    @Column
    Long camperId;

    @Column
    Long drugId;

    @Column
    String time;

    @Column
    Long quantity;
}
