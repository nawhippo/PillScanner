package Nate.PillScanner.Alert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {
    @Autowired
    AlertRepository alertRepository;

    AlertService(AlertRepository alertRepository){
        this.alertRepository = alertRepository;
    }
    List<Alert> findAllDrugAlerts() {
        return alertRepository.findByType("Drug");
   }
}
