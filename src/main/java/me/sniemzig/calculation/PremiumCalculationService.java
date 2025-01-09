package me.sniemzig.calculation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import me.sniemzig.data.DataService;
import me.sniemzig.shared.CalculationParams;
import me.sniemzig.shared.Calculation;
import me.sniemzig.shared.Region;
import me.sniemzig.shared.VehicleType;
import me.sniemzig.statistics.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


/**
 * Service for calculating insurance premiums.
 *
 * <p>This service validates the input parameters, determines the region from
 * the postcode, applies statistical factors, and computes the premium. The
 * result is stored and returned to the user.</p>
 *
 * @see StatisticsService
 * @see DataService
 */
@Service
@RestController
@RequestMapping("/api")
public class PremiumCalculationService {
    final DataService dataService;
    final StatisticsService statisticsService;

    public PremiumCalculationService(DataService dataService, StatisticsService statisticsService) {
        this.dataService = dataService;
        this.statisticsService = statisticsService;
    }

    @Operation(summary = "Calculates the insurance premium.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully computed premium.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Calculation.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid parameter format.",
                    content = @Content(mediaType = "text/plain")),
    })
    @GetMapping("/calculate")
    public ResponseEntity<Object> calculate(@ModelAttribute CalculationParams request) {
        // Validation
        if (request.getKilometers() <= 0)
            return ResponseEntity.badRequest().body("Kilometers must be positive!");

        Optional<VehicleType> vehicleTypeResult = VehicleType.of(request.getVehicleType());
        if (vehicleTypeResult.isEmpty())
            return ResponseEntity.badRequest().body("Unknown vehicle type: " + request.getVehicleType());

        ResponseEntity<Object> regionRequest = dataService.region(request.getPostcode());
        if (!regionRequest.getStatusCode().is2xxSuccessful())
            return ResponseEntity.badRequest().body("Could not resolve region of postcode " + request.getPostcode());
        var region = (Region) regionRequest.getBody(); // safe because request was successful

        // Calculation
        double premium = statisticsService.getBasePremium()
                * statisticsService.kilometerFactor(request.getKilometers())
                * statisticsService.regionalFactor(region)
                * statisticsService.vehicleTypeFactor(vehicleTypeResult.get());

        // Persistence
        var calculation = dataService.storeCalculation(new Calculation(request, round2Decimals(premium)));

        return ResponseEntity.ok(calculation);
    }

    private double round2Decimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
