package com.adps.energy_provider.controller;

import com.adps.energy_provider.dto.BatteryCreateDTO;
import com.adps.energy_provider.utils.BatteriesResponse;
import com.adps.energy_provider.utils.BatteryCapacityResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/batteries")
public interface BatteryController{

    @PostMapping
    public ResponseEntity<BatteriesResponse> addBatteries(@Valid @RequestBody List<BatteryCreateDTO> batteries);
    @GetMapping("/postcode-range")
    public ResponseEntity<BatteryCapacityResponse> getBatteriesWithinPostcodeRange(
            @RequestParam(required = false) Integer postcodeStart,
            @RequestParam(required = false) Integer postcodeEnd);
}
