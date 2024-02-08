package Nate.PillScanner.Security;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VerifyTokenRepository extends JpaRepository<VerifyToken, Long> {
    VerifyToken findByContent(String content);
}
