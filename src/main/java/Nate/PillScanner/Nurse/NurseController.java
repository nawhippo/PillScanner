package Nate.PillScanner.Nurse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/nurse")
public class NurseController {
    private final NurseService nurseService;

    public NurseController(NurseService nurseService) {
        this.nurseService = nurseService;
    }

    @PostMapping("/createNurse")
    public ResponseEntity<Nurse> createNurse(@RequestBody Nurse nurse) {
        Nurse savedNurse = nurseService.saveNurse(nurse);
        return new ResponseEntity<>(savedNurse, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteNurse")
    public ResponseEntity<Void> deleteNurse(@RequestBody Nurse nurse) {
        nurseService.deleteNurse(nurse.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllNurses")
    public ResponseEntity<List<Nurse>> getAllNurses() {
        List<Nurse> nurses = nurseService.findAllNurses();
        return ResponseEntity.ok(nurses);
    }

    @PutMapping("/updateNurse")
    public ResponseEntity<Nurse> updateNurse(@RequestBody Nurse nurse) {
        Nurse updatedNurse = nurseService.updateNurse(nurse);
        return ResponseEntity.ok(updatedNurse);
    }

    @GetMapping("/getNurseById/{id}")
    public ResponseEntity<Optional<Nurse>> getNurseById(@PathVariable Long id) {
        Optional<Nurse> nurse = nurseService.findNurseById(id);
        if (nurse != null) {
            return ResponseEntity.ok(nurse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}