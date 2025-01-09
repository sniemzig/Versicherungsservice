package me.sniemzig.shared;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Optional;

public class RegionDeserializer extends JsonDeserializer<Region> {
    @Override
    public Region deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        Optional<Region> region = Region.of(jsonParser.getText());
        if (region.isEmpty())
            throw new IOException("No valid region code");

        return region.get();
    }
}
