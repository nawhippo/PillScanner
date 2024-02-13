package Nate.PillScanner.Alert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/alert")
public class AlertController {

    @Autowired
    AlertService alertService;

    AlertController(AlertService alertService){
        this.alertService = alertService;
    }

    @GetMapping("/getAllAlerts")
    public ResponseEntity<List<?>> getAllDrugAlerts() {
        return ResponseEntity.ok(alertService.findAllDrugAlerts());
    }

}
