package Nate.PillScanner.Dispense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DispenseRepository extends JpaRepository<Dispense, Long> {

    @Modifying
    @Query("update Dispense d set d.dispensed = false")
    void resetAllDispensedStatus();
}
