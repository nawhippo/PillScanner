package Nate.PillScanner.Shipment;
import Nate.PillScanner.Drug.Drug;
import Nate.PillScanner.Drug.DrugService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final DrugService drugService;

    public ShipmentService(ShipmentRepository shipmentRepository, DrugService drugService) {
        this.shipmentRepository = shipmentRepository;
        this.drugService = drugService;
    }

    public Shipment saveShipment(Shipment shipment) {
        return shipmentRepository.save(shipment);
    }

    public List<Shipment> findAllShipments() {
        return shipmentRepository.findAll();
    }

    public Optional<Shipment> findShipmentById(Long id) {
        return shipmentRepository.findById(id);
    }

    public Shipment updateShipment(Shipment shipment) {
        return shipmentRepository.save(shipment);
    }

    public void deleteShipment(Long id) {
        shipmentRepository.deleteById(id);
    }

    public Shipment createShipment(Shipment shipment) {
        Optional<Drug> optionalDrug = drugService.findDrugById(shipment.getDrugId());

        if (optionalDrug.isPresent()) {
            Drug drug = optionalDrug.get();
            drug.setSupply(drug.getSupply() + shipment.getQuantity());
            drugService.saveDrug(drug);
        }

        return saveShipment(shipment);
    }




}