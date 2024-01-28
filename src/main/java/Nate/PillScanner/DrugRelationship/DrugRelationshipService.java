package Nate.PillScanner.DrugRelationship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DrugRelationshipService {
    private final DrugRelationshipRepository drugRelationshipRepository;

    @Autowired
    public DrugRelationshipService(DrugRelationshipRepository drugRelationshipRepository) {
        this.drugRelationshipRepository = drugRelationshipRepository;
    }

    public DrugRelationship saveDrugRelationship(DrugRelationship drugRelationship) {
        return drugRelationshipRepository.save(drugRelationship);
    }

    public List<DrugRelationship> findAllDrugRelationships() {
        return drugRelationshipRepository.findAll();
    }

    public Optional<DrugRelationship> findDrugRelationshipById(Long id) {
        return drugRelationshipRepository.findById(id);
    }

    public DrugRelationship updateDrugRelationship(DrugRelationship drugRelationship) {
        return drugRelationshipRepository.save(drugRelationship);
    }

    public void deleteDrugRelationship(Long id) {
        drugRelationshipRepository.deleteById(id);
    }
}