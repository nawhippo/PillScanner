package Nate.PillScanner.Dispense;
import Nate.PillScanner.DrugRelationship.DrugRelationship;
import Nate.PillScanner.DrugRelationship.DrugRelationshipRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DispenseGenerationService {

    private final DrugRelationshipRepository drugRelationshipRepository;
    private final DispenseRepository dispenseRepository;

    public DispenseGenerationService(DrugRelationshipRepository drugRelationshipRepository, DispenseRepository dispenseRepository) {
        this.drugRelationshipRepository = drugRelationshipRepository;
        this.dispenseRepository = dispenseRepository;
    }

    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional
    public void generateDailyDispenses() {
        List<DrugRelationship> relationships = drugRelationshipRepository.findAll();

        relationships.forEach(relationship -> {
            Dispense dispense = new Dispense();
            dispense.setDrugId(relationship.getDrugId());
            dispense.setMeal(relationship.getMeal());
            dispense.setDispenseTime(LocalDateTime.now().withHour(calculateHourForMeal(relationship.getMeal())));
            dispense.setQuantity(relationship.getQuantity());
            dispense.setDispensed(false);
            dispenseRepository.save(dispense);
        });
    }

    private int calculateHourForMeal(String meal) {

        return 0;
    }
}