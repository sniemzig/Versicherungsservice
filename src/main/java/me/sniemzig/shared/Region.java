package me.sniemzig.shared;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;

import java.util.Optional;

@JsonDeserialize(using = RegionDeserializer.class)
public enum Region {
    BADEN_WUERTTEMBERG("DE-BW"),
    BAYERN("DE-BY"),
    BERLIN("DE-BE"),
    BRANDENBURG("DE-BB"),
    BREMEN("DE-HB"),
    HAMBURG("DE-HH"),
    HESSEN("DE-HE"),
    MECKLENBURG_VORPOMMERN("DE-MV"),
    NIEDERSACHSEN("DE-NI"),
    NORDRHEIN_WESTFALEN("DE-NW"),
    RHEINLAND_PFALZ("DE-RP"),
    SAARLAND("DE-SL"),
    SACHSEN("DE-SN"),
    SACHSEN_ANHALT("DE-ST"),
    SCHLESWIG_HOLSTEIN("DE-SH"),
    THUERINGEN("DE-TH");

    private final String identifier;

    Region(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static Optional<Region> of(String identifier) {
        for (Region region : Region.values())
            if (region.getIdentifier().equals(identifier))
                return Optional.of(region);
        return Optional.empty();
    }
}