package Nate.PillScanner.Dispense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DispenseRepository extends JpaRepository<Dispense, Long> {

    @Modifying
    @Query("update Dispense d set d.dispensed = false")
    void resetAllDispensedStatus();

    List<Dispense> findByDispensedTrue();


    List<Dispense> findByDispensedFalse();


    @Query("SELECT d FROM Dispense d WHERE d.meal = :mealType AND d.time <= :now AND d.missed = false")
    List<Dispense> findMissableDispensesForMealType(@Param("mealType") String mealType, @Param("now") LocalDateTime now);
}
