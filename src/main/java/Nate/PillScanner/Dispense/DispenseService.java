package Nate.PillScanner.Dispense;

import Nate.PillScanner.Alert.Alert;
import Nate.PillScanner.Alert.AlertRepository;
import Nate.PillScanner.Drug.Drug;
import Nate.PillScanner.Drug.DrugRepository;
import Nate.PillScanner.Drug.DrugService;
import Nate.PillScanner.DrugRelationship.DrugRelationship;
import Nate.PillScanner.Nurse.Nurse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
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



    public Dispense confirmDispense(Dispense dispense, Nurse nurse) {
        Drug drug = drugRepository.findById(dispense.getDrugId())
                .orElseThrow(() -> new NoSuchElementException("Drug not found"));
        drug.setSupply(drug.getSupply() - dispense.getQuantity());
        dispense.setDispensed(true);
        dispense.setNurseId(nurse.getId());
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateFormat.format(date);
        dispense.setTime(dateString);
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