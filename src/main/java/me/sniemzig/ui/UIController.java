package me.sniemzig.ui;

import me.sniemzig.calculation.PremiumCalculationService;
import me.sniemzig.data.DataService;
import me.sniemzig.shared.Calculation;
import me.sniemzig.shared.CalculationParams;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UIController {
    private final PremiumCalculationService premiumCalculationService;
    private final DataService dataService;

    public UIController(PremiumCalculationService premiumCalculationService, DataService dataService) {
        this.premiumCalculationService = premiumCalculationService;
        this.dataService = dataService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("vehicleTypes", dataService.getVehicleTypes());
        return "index";
    }

    @GetMapping("/calculate")
    public String calculate(@RequestParam int kilometers,
                            @RequestParam String vehicleType,
                            @RequestParam int postcode,
                            Model model) {
        model.addAttribute("kilometers", kilometers);
        model.addAttribute("vehicleType", vehicleType);
        model.addAttribute("postcode", postcode);

        CalculationParams request = new CalculationParams();
        request.setPostcode(postcode);
        request.setVehicleType(vehicleType);
        request.setKilometers(kilometers);

        ResponseEntity<Object> result = premiumCalculationService.calculate(request);

        if (result.getStatusCode().is2xxSuccessful()) {
            Calculation calculation = (Calculation) result.getBody();
            model.addAttribute("calculation", calculation);
        } else {
            model.addAttribute("error", result.getBody());
        }

        model.addAttribute("vehicleTypes", dataService.getVehicleTypes());
        return "index";
    }
}
