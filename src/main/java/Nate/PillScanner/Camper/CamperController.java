package Nate.PillScanner.Camper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/camper")
public class CamperController {
    private final CamperService camperService;

    @Autowired
    public CamperController(CamperService camperService) {
        this.camperService = camperService;
    }

    @PostMapping("/createCamper")
    public ResponseEntity<Camper> createCamper(@RequestBody Camper camper) {
        Camper savedCamper = camperService.saveCamper(camper);
        return new ResponseEntity<>(savedCamper, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteCamper")
    public ResponseEntity<Void> deleteCamper(@RequestBody Camper camper) {
        camperService.deleteCamper(camper.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllCampers")
    public ResponseEntity<List<Camper>> getAllCampers() {
        List<Camper> campers = camperService.findAllCampers();
        return ResponseEntity.ok(campers);
    }

    @PutMapping("/updateCamper")
    public ResponseEntity<Camper> updateCamper(@RequestBody Camper camper) {
        Camper updatedCamper = camperService.updateCamper(camper);
        return ResponseEntity.ok(updatedCamper);
    }
}