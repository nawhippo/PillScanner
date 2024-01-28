package Nate.PillScanner.Drug;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {
}
