package Nate.PillScanner.Nurse;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NurseService {
    private final NurseRepository nurseRepository;
    private final PasswordEncoder passwordEncoder;

    public NurseService(NurseRepository nurseRepository, PasswordEncoder passwordEncoder) {
        this.nurseRepository = nurseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Nurse saveNurse(Nurse nurse) {
        nurse.setPassword(passwordEncoder.encode(nurse.getPassword()));
        return nurseRepository.save(nurse);
    }

    public List<Nurse> findAllNurses() {
        return nurseRepository.findAll();
    }

    public Optional<Nurse> findNurseById(Long id) {
        return nurseRepository.findById(id);
    }

    public Nurse updateNurse(Nurse nurse) {
        return nurseRepository.save(nurse);
    }

    public void deleteNurse(Long id) {
        nurseRepository.deleteById(id);
    }
}