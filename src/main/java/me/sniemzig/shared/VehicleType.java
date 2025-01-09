package me.sniemzig.shared;

import java.util.Optional;

public enum VehicleType {
    COUPE("Coupe"),
    CABRIOLET("Cabriolet"),
    LIMOUSINE("Limousine"),
    KOMBI("Kombi"),
    SUV("Suv"),
    MINIVAN("Minivan"),
    GELAENDEWAGEN("Gelaendewagen"),
    WOHNMOBIL("Wohnmobil");

    private final String identifier;

    VehicleType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static Optional<VehicleType> of(String identifier) {
        try {
            return Optional.of(VehicleType.valueOf(identifier.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
