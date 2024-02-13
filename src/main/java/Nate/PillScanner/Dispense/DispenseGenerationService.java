package Nate.PillScanner.Dispense;
import Nate.PillScanner.Alert.Alert;
import Nate.PillScanner.Alert.AlertRepository;
import Nate.PillScanner.DrugRelationship.DrugRelationship;
import Nate.PillScanner.DrugRelationship.DrugRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DispenseGenerationService {

    @Autowired
    private final DrugRelationshipRepository drugRelationshipRepository;
    @Autowired
    private final DispenseRepository dispenseRepository;
    @Autowired
    private final AlertRepository alertRepository;

    @Scheduled(cron = "0 0 6 * * *")
    @Transactional
    public void generateBreakfastDispenses() {
        generateDispensesForMealType("Breakfast");
    }

    @Scheduled(cron = "0 0 11 * * *")
    @Transactional
    public void generateLunchDispenses() {
        generateDispensesForMealType("Lunch");
    }

    @Scheduled(cron = "0 0 16 * * *")
    @Transactional
    public void generateDinnerDispenses() {
        generateDispensesForMealType("Dinner");
    }



    public DispenseGenerationService(DrugRelationshipRepository drugRelationshipRepository, DispenseRepository dispenseRepository, AlertRepository alertRepository) {
        this.drugRelationshipRepository = drugRelationshipRepository;
        this.dispenseRepository = dispenseRepository;
        this.alertRepository = alertRepository;
    }


    public void generateDispensesForMealType(String mealType) {
        List<DrugRelationship> drugsToDispense = drugRelationshipRepository.findByMealType(mealType);

        drugsToDispense.forEach(drug -> {
            Dispense newDispense = new Dispense();
            newDispense.setDrugId(drug.getDrugId());
            newDispense.setQuantity(drug.getQuantity());
            newDispense.setMeal(mealType);
            newDispense.setCamperId(drug.getCamperId());
            newDispense.setMissed(false);

            dispenseRepository.save(newDispense);
        });
    }
}