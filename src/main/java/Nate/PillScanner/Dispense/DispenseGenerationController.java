package Nate.PillScanner.Dispense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/DispenseGenerate")
public class DispenseGenerationController {

    private final DispenseGenerationService dispenseGenerationService;

    @Autowired
    public DispenseGenerationController(DispenseGenerationService dispenseGenerationService) {
        this.dispenseGenerationService = dispenseGenerationService;
    }

    @PostMapping("/generateDispenses/")
    public ResponseEntity<?> generateDispenses() {
                dispenseGenerationService.generateBreakfastDispenses();
                dispenseGenerationService.generateLunchDispenses();
                dispenseGenerationService.generateDinnerDispenses();
                return ResponseEntity.ok("Dispenses generated");
        }
    }