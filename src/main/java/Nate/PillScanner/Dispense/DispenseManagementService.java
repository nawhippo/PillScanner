package Nate.PillScanner.Dispense;

import Nate.PillScanner.Alert.Alert;
import Nate.PillScanner.Alert.AlertRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DispenseManagementService {

    private final DispenseRepository dispenseRepository;
    private final AlertRepository alertRepository;
    public DispenseManagementService(DispenseRepository dispenseRepository, AlertRepository alertRepository) {
        this.dispenseRepository = dispenseRepository;
        this.alertRepository = alertRepository;
    }

    @Scheduled(cron = "0 0 10 * * *") // 10 AM for Breakfast
    @Transactional
    public void markMissedBreakfasts() {
        markMissedDispenses("Breakfast");
    }

    @Scheduled(cron = "0 0 14 * * *") // 2 PM for Lunch
    @Transactional
    public void markMissedLunches() {
        markMissedDispenses("Lunch");
    }

    @Scheduled(cron = "0 0 19 * * *") // 7 PM for Dinner
    @Transactional
    public void markMissedDinners() {
        markMissedDispenses("Dinner");
    }

    private void markMissedDispenses(String mealType) {
        LocalDateTime now = LocalDateTime.now();
        // Assume findMissableDispensesForMealType is modified to accept a String for mealType
        List<Dispense> missableDispenses = dispenseRepository.findMissableDispensesForMealType(mealType, now);

        missableDispenses.forEach(dispense -> {
            dispense.setMissed(true);
            Alert alert = new Alert();
            alert.setDescription("MISSED DISPENSE: " + mealType.toUpperCase());
            alert.setType("Drug");
            alert.setObjectId(dispense.getDrugId());
            alertRepository.save(alert);
            dispenseRepository.save(dispense);
        });
    }
}