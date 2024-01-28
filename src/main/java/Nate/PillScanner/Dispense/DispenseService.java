package Nate.PillScanner.Dispense;

import Nate.PillScanner.Drug.Drug;
import Nate.PillScanner.Drug.DrugService;
import Nate.PillScanner.Shipment.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DispenseService {

    private final DispenseRepository dispenseRepository;
    private final DrugService drugService;

    @Autowired
    public DispenseService(DispenseRepository dispenseRepository, DrugService drugService) {
        this.dispenseRepository = dispenseRepository;
        this.drugService = drugService;
    }

    public Dispense saveDispense(Dispense dispense) {
        return dispenseRepository.save(dispense);
    }

    public List<Dispense> findAllDispenses() {
        return dispenseRepository.findAll();
    }

    public Optional<Dispense> findDispenseById(Long id) {
        return dispenseRepository.findById(id);
    }

    public Dispense updateDispense(Dispense dispense) {
        return dispenseRepository.save(dispense);
    }

    public void deleteDispense(Long id) {
        dispenseRepository.deleteById(id);
    }


    public Dispense createDispense(Dispense dispense) {
        Optional<Drug> optionalDrug = drugService.findDrugById(dispense.getDrugId());

        if (optionalDrug.isPresent()) {
            Drug drug = optionalDrug.get();
            drug.setSupply(drug.getSupply() - dispense.getQuantity());
            drugService.saveDrug(drug);
        }

        return saveDispense(dispense);
    }
}