package me.sniemzig.statistics;

import me.sniemzig.shared.Region;
import me.sniemzig.shared.VehicleType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsServiceTest {
    private final StatisticsService statService = new StatisticsService();

    @Test
    void regionalFactor() {
        for (Region region : Statistics.regionRiskFactor.keySet())
            assertEquals(Statistics.regionRiskFactor.get(region), Statistics.regionRiskFactor.get(region));
    }

    @Test
    void kilometerFactor() {
        assertEquals(0.5, statService.kilometerFactor( 2_000));
        assertEquals(0.5, statService.kilometerFactor( 5_000));

        assertEquals(1.0, statService.kilometerFactor( 5_001));
        assertEquals(1.0, statService.kilometerFactor(10_000));

        assertEquals(1.5, statService.kilometerFactor(10_001));
        assertEquals(1.5, statService.kilometerFactor(20_000));

        assertEquals(2.0, statService.kilometerFactor(20_001));
        assertEquals(2.0, statService.kilometerFactor(30_000));

    }

    @Test
    void vehicleTypeFactor() {
        for (VehicleType vehicleType : Statistics.vehicleRiskFactors.keySet())
            assertEquals(Statistics.vehicleRiskFactors.get(vehicleType), Statistics.vehicleRiskFactors.get(vehicleType));
    }
}