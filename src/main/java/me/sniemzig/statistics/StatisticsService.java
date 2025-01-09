package me.sniemzig.statistics;

import me.sniemzig.shared.Region;
import me.sniemzig.shared.VehicleType;
import org.springframework.stereotype.Service;

/**
 * Service for calculating premium factors using fixed values (for now).
 * <p>
 * This service currently provides basic premium factors based on hardcoded values.
 * These include:
 * <ul>
 *     <li>Base premium</li>
 *     <li>Region risk factor</li>
 *     <li>Kilometer adjustment</li>
 *     <li>Vehicle type adjustment</li>
 * </ul>
 * In the future, this service may handle more complex and dynamic statistical calculations.
 * </p>
 */
@Service
public class StatisticsService {
    public double getBasePremium() {
        return 500;
    }

    public double regionalFactor(Region region) {
        return Statistics.regionRiskFactor.get(region);
    }

    public double kilometerFactor(int kilometers) {
        assert kilometers >= 0;
        if (kilometers <  5_001) return 0.5;
        if (kilometers < 10_001) return 1.;
        if (kilometers < 20_001) return 1.5;
        return 2.;
    }

    public double vehicleTypeFactor(VehicleType type) {
        return Statistics.vehicleRiskFactors.get(type);
    }
}
