package Nate.PillScanner.Camper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CamperService {
    private final CamperRepository camperRepository;

    @Autowired
    public CamperService(CamperRepository camperRepository) {
        this.camperRepository = camperRepository;
    }

    public Camper saveCamper(Camper camper) {
        return camperRepository.save(camper);
    }

    public List<Camper> findAllCampers() {
        return camperRepository.findAll();
    }

    public Optional<Camper> findCamperById(Long id) {
        return camperRepository.findById(id);
    }

    public Camper updateCamper(Camper camper) {
        return camperRepository.save(camper);
    }

    public void deleteCamper(Long id) {
        camperRepository.deleteById(id);
    }
}