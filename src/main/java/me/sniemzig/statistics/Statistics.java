package me.sniemzig.statistics;

import me.sniemzig.shared.Region;
import me.sniemzig.shared.VehicleType;

import java.util.Map;

public final class Statistics {
    public static final Map<VehicleType, Double> vehicleRiskFactors = Map.ofEntries(
            Map.entry(VehicleType.COUPE, 1.2),
            Map.entry(VehicleType.CABRIOLET, 1.5),
            Map.entry(VehicleType.LIMOUSINE, 1.0),
            Map.entry(VehicleType.KOMBI, 1.1),
            Map.entry(VehicleType.SUV, 1.3),
            Map.entry(VehicleType.MINIVAN, 1.0),
            Map.entry(VehicleType.GELAENDEWAGEN, 1.4),
            Map.entry(VehicleType.WOHNMOBIL, 1.3)
    );

    public static final Map<Region, Double> regionRiskFactor = Map.ofEntries(
            Map.entry(Region.BADEN_WUERTTEMBERG, 1.3),
            Map.entry(Region.BAYERN, 1.1),
            Map.entry(Region.BERLIN, 1.5),
            Map.entry(Region.BRANDENBURG, 1.0),
            Map.entry(Region.BREMEN, 1.4),
            Map.entry(Region.HAMBURG, 1.3),
            Map.entry(Region.HESSEN, 1.2),
            Map.entry(Region.MECKLENBURG_VORPOMMERN, 0.9),
            Map.entry(Region.NIEDERSACHSEN, 1.1),
            Map.entry(Region.NORDRHEIN_WESTFALEN, 1.3),
            Map.entry(Region.RHEINLAND_PFALZ, 1.1),
            Map.entry(Region.SAARLAND, 1.2),
            Map.entry(Region.SACHSEN, 1.0),
            Map.entry(Region.SACHSEN_ANHALT, 0.9),
            Map.entry(Region.SCHLESWIG_HOLSTEIN, 0.9),
            Map.entry(Region.THUERINGEN, 0.9)
    );
}
