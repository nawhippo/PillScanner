package Nate.PillScanner.DrugRelationship;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/drugRelationship")
public class DrugRelationshipController {
    private final DrugRelationshipService drugRelationshipService;

    public DrugRelationshipController(DrugRelationshipService drugRelationshipService) {
        this.drugRelationshipService = drugRelationshipService;
    }

    @PostMapping("/createDrugRelationship")
    public ResponseEntity<DrugRelationship> createDrugRelationship(@RequestBody DrugRelationship drugRelationship) {
        DrugRelationship savedDrugRelationship = drugRelationshipService.saveDrugRelationship(drugRelationship);
        return new ResponseEntity<>(savedDrugRelationship, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteDrugRelationship")
    public ResponseEntity<Void> deleteDrugRelationship(@RequestBody DrugRelationship drugRelationship) {
        drugRelationshipService.deleteDrugRelationship(drugRelationship.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllDrugRelationships")
    public ResponseEntity<List<DrugRelationship>> getAllDrugRelationships() {
        List<DrugRelationship> drugRelationships = drugRelationshipService.findAllDrugRelationships();
        return ResponseEntity.ok(drugRelationships);
    }

    @PutMapping("/updateDrugRelationship")
    public ResponseEntity<DrugRelationship> updateDrugRelationship(@RequestBody DrugRelationship drugRelationship) {
        DrugRelationship updatedDrugRelationship = drugRelationshipService.updateDrugRelationship(drugRelationship);
        return ResponseEntity.ok(updatedDrugRelationship);
    }
}