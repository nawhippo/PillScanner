package Nate.PillScanner.Drug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/drug")
public class DrugController {
    private final DrugService drugService;

    @Autowired
    public DrugController(DrugService drugService) {
        this.drugService = drugService;
    }

    @PostMapping("/createDrug")
    public ResponseEntity<Drug> createDrug(@RequestBody Drug drug) {
        Drug savedDrug = drugService.saveDrug(drug);
        return new ResponseEntity<>(savedDrug, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteDrug")
    public ResponseEntity<Void> deleteDrug(@RequestBody Drug drug) {
        drugService.deleteDrug(drug.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllDrugs")
    public ResponseEntity<List<Drug>> getAllDrugs() {
        List<Drug> drugs = drugService.findAllDrugs();
        return ResponseEntity.ok(drugs);
    }

    @GetMapping("/getDrug/{drugId}")
    public ResponseEntity<Optional<Drug>> getDrug(@PathVariable Long drugId) {
        Optional<Drug> outputDrug = drugService.findDrugById(drugId);
        return ResponseEntity.ok(outputDrug);
    }

    @PutMapping("/updateDrug")
    public ResponseEntity<Drug> updateDrug(@RequestBody Drug drug) {
        Drug updatedDrug = drugService.updateDrug(drug);
        return ResponseEntity.ok(updatedDrug);
    }
}