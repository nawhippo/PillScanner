package Nate.PillScanner;

import Nate.PillScanner.Alert.AlertRepository;
import Nate.PillScanner.Camper.Camper;
import Nate.PillScanner.Dispense.DispenseManagementService;
import Nate.PillScanner.Dispense.DispenseRepository;
import Nate.PillScanner.Drug.Drug;
import Nate.PillScanner.DrugRelationship.DrugRelationship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class DispenseManagementServiceTest {

    @Mock
    private DispenseRepository dispenseRepository;

    @Mock
    private AlertRepository alertRepository;

    // Define a test-specific subclass of DispenseManagementService that overrides time-related behavior
    private static class TestDispenseManagementService extends DispenseManagementService {
        private LocalDateTime testTime;

        public TestDispenseManagementService(DispenseRepository dispenseRepository, AlertRepository alertRepository, LocalDateTime testTime) {
            super(dispenseRepository, alertRepository);
            this.testTime = testTime;
        }

        protected LocalDateTime getCurrentTime() {
            return testTime;
        }
    }

    private DispenseManagementService testService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        LocalDateTime fixedTestTime = LocalDateTime.of(2024, 2, 10, 10, 0);
        testService = new TestDispenseManagementService(dispenseRepository, alertRepository, fixedTestTime);
    }

    @Test
    void testMarkMissedDispenses() {
        // Setup your test scenario...
        DrugRelationship drugRelationship = new DrugRelationship();
        Camper camper =  new Camper();
        camper.setId(1L);
        camper.setFirstName("Gary");
        camper.setLastName("Busey");
        camper.setUnit("D7");
        Drug drug = new Drug();
        drug.setId(2);
        drug.setSupply(30L);
        drug.setMg(20);
        drug.setName("Foo");
        drug.setDescription("Bar");

        testService.markMissedDispenses("Breakfast");
        verify(dispenseRepository, times(1)).findMissableDispensesForMealType(eq("Breakfast"), any(LocalDateTime.class));
    }
}