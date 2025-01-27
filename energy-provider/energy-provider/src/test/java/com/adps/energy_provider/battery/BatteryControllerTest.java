package com.adps.energy_provider.battery;

import com.adps.energy_provider.controller.impl.BatteryControllerImpl;
import com.adps.energy_provider.dto.BatteryCapacityResponseDTO;
import com.adps.energy_provider.dto.BatteryCreateDTO;
import com.adps.energy_provider.dto.BatteryDTO;
import com.adps.energy_provider.service.impl.BatteryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BatteryControllerImpl.class)
public class BatteryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BatteryServiceImpl service;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void whenAddBatteries_Success() throws Exception {
        List<BatteryCreateDTO> requestBatteries = List.of(new BatteryCreateDTO("AA1", 200, 1000));
        List<BatteryDTO> responseBatteries = List.of(new BatteryDTO(1L, "AA1", 200, 1000));

        when(service.addBatteries(requestBatteries)).thenReturn(responseBatteries);

        mockMvc.perform(post("/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBatteries)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Batteries added successfully"))
                .andExpect(jsonPath("$.data.size()").value(1));

        verify(service, times(1)).addBatteries(requestBatteries);
    }

    @Test
    void whenAddBatteries_EmptyBody() throws Exception {
        when(service.addBatteries(List.of())).thenThrow(new IllegalArgumentException("No batteries to add"));

        mockMvc.perform(post("/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("No batteries to add"));
    }

    @Test
    void whenGetBatteriesWithinPostcodeRange_Success() throws Exception {
        int postcodeStart = 150;
        int postcodeEnd = 400;

        BatteryCapacityResponseDTO responseDTO = new BatteryCapacityResponseDTO(Arrays.asList("AA1", "BB1"), 1500.0, 3000L);

        when(service.getBatteriesWithinPostcodeRange(postcodeStart, postcodeEnd)).thenReturn(responseDTO);

        mockMvc.perform(get("/batteries/postcode-range")
                        .param("postcodeStart", String.valueOf(postcodeStart))
                        .param("postcodeEnd", String.valueOf(postcodeEnd))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Batteries within postcode range fetched successfully"))
                .andExpect(jsonPath("$.data.names.size()").value(2))
                .andExpect(jsonPath("$.data.averageWattCapacity").value(1500.0))
                .andExpect(jsonPath("$.data.totalWattCapacity").value(3000L));

        verify(service, times(1)).getBatteriesWithinPostcodeRange(postcodeStart, postcodeEnd);
    }

    @Test
    void whenGetBatteriesWithinPostcodeRange_NoDataFound() throws Exception {
        int postcodeStart = 150;
        int postcodeEnd = 400;

        when(service.getBatteriesWithinPostcodeRange(postcodeStart, postcodeEnd)).thenThrow(new NoSuchElementException("No batteries found in given postcode range"));

        mockMvc.perform(get("/batteries/postcode-range")
                        .param("postcodeStart", String.valueOf(postcodeStart))
                        .param("postcodeEnd", String.valueOf(postcodeEnd))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No batteries found in given postcode range"));

        verify(service, times(1)).getBatteriesWithinPostcodeRange(postcodeStart, postcodeEnd);
    }
}
