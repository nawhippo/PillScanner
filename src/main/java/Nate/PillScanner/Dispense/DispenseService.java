package Nate.PillScanner.Dispense;

import Nate.PillScanner.Alert.Alert;
import Nate.PillScanner.Alert.AlertRepository;
import Nate.PillScanner.Drug.Drug;
import Nate.PillScanner.Drug.DrugRepository;
import Nate.PillScanner.Drug.DrugService;
import Nate.PillScanner.DrugRelationship.DrugRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DispenseService {

    private final DispenseRepository dispenseRepository;
    private final DrugRepository drugRepository;
    private final DrugService drugService;

    private final AlertRepository alertRepository;
    @Autowired
    public DispenseService(DispenseRepository dispenseRepository, DrugRepository drugRepository, DrugService drugService, AlertRepository alertRepository) {
        this.dispenseRepository = dispenseRepository;
        this.drugRepository = drugRepository;
        this.drugService = drugService;
        this.alertRepository = alertRepository;
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

    public List<Dispense> findAllDispensed() {
        return dispenseRepository.findByDispensedTrue();
    }

    public List<Dispense> findAllNonDispensed() {
        return dispenseRepository.findByDispensedTrue();
    }

    public void deleteDispense(Long id) {
        dispenseRepository.deleteById(id);
    }


    public Dispense createDispense(DrugRelationship drugRelationship) {
        Dispense dispense = new Dispense();
        Drug drug = drugService.findDrugById(drugRelationship.getDrugId())
                .orElseThrow(() -> new NoSuchElementException("Drug not found"));
        dispense.setDrugId(drug.getId());
        dispense.setMeal(drugRelationship.getMeal());
        dispense.setQuantity(drugRelationship.getQuantity());
        dispense.setDispensed(false);
        return saveDispense(dispense);
    }

    public Dispense confirmDispense(Dispense dispense) {
        Drug drug = drugRepository.findById(dispense.getDrugId())
                .orElseThrow(() -> new NoSuchElementException("Drug not found"));
        drug.setSupply(drug.getSupply() - dispense.getQuantity());
        dispense.setDispensed(true);
        dispense.setDispenseTime(LocalDateTime.now());
        drugRepository.save(drug);
        if(drug.getSupply() <= 25){
            Alert alert = new Alert();
            alert.setObjectId(drug.getId());
            alert.setType("Drug");
            alert.setDescription("Supply of 25 or Less");
            alertRepository.save(alert);
        }
        return dispenseRepository.save(dispense);
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void resetDispensedStatus() {
        dispenseRepository.resetAllDispensedStatus();
    }


}