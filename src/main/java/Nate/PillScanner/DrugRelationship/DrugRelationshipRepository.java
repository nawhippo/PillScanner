package Nate.PillScanner.DrugRelationship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  DrugRelationshipRepository extends JpaRepository<DrugRelationship, Long> {
    @Query("SELECT dr FROM DrugRelationship dr JOIN dr.meals meal WHERE meal = :mealType")
    List<DrugRelationship> findByMealType(String mealType);
}
