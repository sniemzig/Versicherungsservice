package me.sniemzig.calculation;

import me.sniemzig.data.DataService;
import me.sniemzig.shared.Region;
import me.sniemzig.statistics.StatisticsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PremiumCalculationService.class)
class PremiumCalculationServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DataService dataService;

    @MockitoBean
    StatisticsService statisticsService;

    @Test
    void calculate1() throws Exception {
        when(dataService.region(anyInt())).thenReturn(ResponseEntity.ok(Region.NORDRHEIN_WESTFALEN));
        when(dataService.storeCalculation(any())).thenAnswer(i -> i.getArgument(0));
        when(statisticsService.getBasePremium()).thenReturn(500.);
        when(statisticsService.kilometerFactor(anyInt())).thenReturn(1.);
        when(statisticsService.regionalFactor(any())).thenReturn(2.);
        when(statisticsService.vehicleTypeFactor(any())).thenReturn(3.);

        mockMvc.perform(get("/api/calculate")
                        .param("kilometers", "1000")
                        .param("postcode", "12345")
                        .param("vehicleType", "Coupe")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.premium").value(500. * 1. * 2. * 3.))
                .andExpect(jsonPath("$.request.kilometers").value(1000))
                .andExpect(jsonPath("$.request.postcode").value(12345))
                .andExpect(jsonPath("$.request.vehicleType").value("Coupe"));

        verify(dataService, times(1)).region(12345);
        verify(statisticsService, times(1)).getBasePremium();
    }
}