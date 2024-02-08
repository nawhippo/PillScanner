package Nate.PillScanner.Nurse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {
    Nurse findByUsername(String username);
    Nurse findByVerifyTokenId(Long tokenId);
    Nurse findByEmail(String email);
}