package Nate.PillScanner.Camper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CamperRepository extends JpaRepository<Camper, Long> {
}
