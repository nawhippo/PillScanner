package Nate.PillScanner.Dispense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dispense")
public class DispenseController {
    private final DispenseService dispenseService;

    @Autowired
    public DispenseController(DispenseService dispenseService) {
        this.dispenseService = dispenseService;
    }

    @PostMapping("/createDispense")
    public ResponseEntity<Dispense> createDispense(@RequestBody Dispense dispense) {
        Dispense savedDispense = dispenseService.saveDispense(dispense);
        return new ResponseEntity<>(savedDispense, HttpStatus.CREATED);
    }

    @GetMapping("/getAllDispenses")
    public ResponseEntity<List<Dispense>> getAllDispenses() {
        List<Dispense> dispenses = dispenseService.findAllDispenses();
        return ResponseEntity.ok(dispenses);
    }

    @GetMapping("/getDispense/{dispenseId}")
    public ResponseEntity<Optional<Dispense>> getDispense(@PathVariable Long dispenseId) {
        Optional<Dispense> dispense = dispenseService.findDispenseById(dispenseId);
        return ResponseEntity.ok(dispense);
    }

    @PutMapping("/confirmDispense")
    public ResponseEntity<Dispense> confirmDispense(@RequestBody Dispense dispense) {
        Dispense updatedDispense = dispenseService.confirmDispense(dispense);
        return ResponseEntity.ok(updatedDispense);
    }

    @DeleteMapping("/deleteDispense/{dispenseId}")
    public ResponseEntity<Void> deleteDispense(@PathVariable Long dispenseId) {
        dispenseService.deleteDispense(dispenseId);
        return ResponseEntity.noContent().build();
    }
}