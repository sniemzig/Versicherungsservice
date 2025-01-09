package me.sniemzig.data;

import me.sniemzig.shared.Calculation;
import me.sniemzig.shared.CalculationParams;
import me.sniemzig.shared.Region;
import me.sniemzig.shared.VehicleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataServiceTest {
    @Mock
    private CalculationRepository calculationRepository;

    @InjectMocks
    private DataService dataService;

    @Test
    void getVehicleTypes() {
        Set<String> vehicleTypes = dataService.getVehicleTypes();

        for (var vehicleType : VehicleType.values())
            assertTrue(vehicleTypes.contains(vehicleType.getIdentifier()));
    }

    @Test
    void validRegion() {
        ResponseEntity<Object> response = dataService.region(53797);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(Region.NORDRHEIN_WESTFALEN, response.getBody());
    }

    @Test
    void invalidRegion() {
        ResponseEntity<Object> response = dataService.region(99999);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Invalid postcode", response.getBody());
    }

    @Test
    void storeCalculation() {
        CalculationParams params = new CalculationParams();
        Calculation calculation = new Calculation(params, 100.00);

        when(calculationRepository.save(any(Calculation.class))).thenReturn(calculation);

        Calculation storedCalculation = dataService.storeCalculation(calculation);

        verify(calculationRepository, times(1)).save(calculation);

        assertNotNull(storedCalculation);
        assertEquals(calculation, storedCalculation);
    }
}