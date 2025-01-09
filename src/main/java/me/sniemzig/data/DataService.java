package me.sniemzig.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import me.sniemzig.shared.Calculation;
import me.sniemzig.data.db.CalculationRepository;
import me.sniemzig.shared.Region;
import me.sniemzig.shared.VehicleType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for handling operations lik retrieving possible parameters related to insurance calculations.
 *
 * <p>This service provides endpoints for retrieving vehicle types and resolving
 * regions based on postcodes. It also handles the interaction to the database such as storing the calculations.</p>
 */
@Service
@RestController
@RequestMapping("/api/data")
public class DataService {
    final CalculationRepository calculationRepository;

    final HashMap<Integer, Region> postcode_mapping;

    public DataService(CalculationRepository calculationRepository) {
        this.calculationRepository = calculationRepository;
        this.postcode_mapping = get_postcode_mapping();
    }

    @Operation(summary = "Get allowed vehicle types.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all vehicle Types.",
            content = @Content(
                    mediaType = "application/json", schema = @Schema(type = "array", implementation = String.class)
            )
    )
    @GetMapping("/vehicle-types")
    public Set<String> getVehicleTypes() {
        return Arrays.stream(VehicleType.values()).map(VehicleType::getIdentifier).collect(Collectors.toSet());
    }

    @Operation(summary = "Resolves postcode to Region.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully resolved Region of the given postcode.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Postcode is invalid.",
                    content = @Content(mediaType = "plain/text", schema = @Schema(implementation = String.class))),
    })
    @GetMapping("/region/{postcode}")
    public ResponseEntity<Object> region(@PathVariable int postcode) {
        Region region = postcode_mapping.get(postcode);
        if (region == null)
            return ResponseEntity.badRequest().body("Invalid postcode");

        return ResponseEntity.ok(region);
    }

    @Operation(summary = "Returns all previous calculations.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all previous calculations.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = Calculation.class)
            )
    )
    @GetMapping("/calculations")
    public Iterable<Calculation> getCalculations() {
        return calculationRepository.findAll();
    }

    public Calculation storeCalculation(Calculation calculation) {
        return calculationRepository.save(calculation);
    }

    private HashMap<Integer, Region> get_postcode_mapping() {
        var objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(readPostcodeFile(), new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readPostcodeFile() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("postcode_mapping.json")) {
            if (in == null)
                throw new IllegalArgumentException("Resource not found: " + "postcode_mapping.json");
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
