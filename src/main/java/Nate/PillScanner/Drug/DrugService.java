package Nate.PillScanner.Drug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DrugService {
    private final DrugRepository drugRepository;

    @Autowired
    public DrugService(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    public Drug saveDrug(Drug drug) {
        return drugRepository.save(drug);
    }


    public List<Drug> findAllDrugs() {
        return drugRepository.findAll();
    }

    public Optional<Drug> findDrugById(Long id) {
        return drugRepository.findById(id);
    }


    public Drug updateDrug(Drug drug) {
        return drugRepository.save(drug);
    }


    public void deleteDrug(Long id) {
        drugRepository.deleteById(id);
    }
}