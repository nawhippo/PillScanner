package Nate.PillScanner.Shipment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shipment")
public class ShipmentController {
    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping("/createShipment")
    public ResponseEntity<Shipment> createShipment(@RequestBody Shipment shipment) {
        Shipment savedShipment = shipmentService.createShipment(shipment);
        return new ResponseEntity<>(savedShipment, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteShipment")
    public ResponseEntity<Void> deleteShipment(@RequestBody Shipment shipment) {
        shipmentService.deleteShipment(shipment.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllShipments")
    public ResponseEntity<List<Shipment>> getAllShipments() {
        List<Shipment> shipments = shipmentService.findAllShipments();
        return ResponseEntity.ok(shipments);
    }

    @PutMapping("/updateShipment")
    public ResponseEntity<Shipment> updateShipment(@RequestBody Shipment shipment) {
        Shipment updatedShipment = shipmentService.updateShipment(shipment);
        return ResponseEntity.ok(updatedShipment);
    }

    @GetMapping("/{shipmentId}")
    public ResponseEntity<Optional<Shipment>> getShipmentById(@PathVariable long shipmentId){
        Optional<Shipment> shipment = shipmentService.findShipmentById(shipmentId);
        return ResponseEntity.ok(shipment);
    }
}