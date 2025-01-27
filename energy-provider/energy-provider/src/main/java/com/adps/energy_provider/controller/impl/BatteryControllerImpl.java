package com.adps.energy_provider.controller.impl;

import com.adps.energy_provider.controller.BatteryController;
import com.adps.energy_provider.dto.BatteryCapacityResponseDTO;
import com.adps.energy_provider.dto.BatteryCreateDTO;
import com.adps.energy_provider.dto.BatteryDTO;
import com.adps.energy_provider.service.impl.BatteryServiceImpl;
import com.adps.energy_provider.utils.BatteriesResponse;
import com.adps.energy_provider.utils.BatteryCapacityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class BatteryControllerImpl implements BatteryController {

    @Autowired
    private BatteryServiceImpl service;

    @Override
    public ResponseEntity<BatteriesResponse> addBatteries(@RequestBody List<BatteryCreateDTO> batteries) {
        BatteriesResponse response = new BatteriesResponse();
        try {
            List<BatteryDTO> addedBatteries = service.addBatteries(batteries);

            response.setMessage("Batteries added successfully");
            response.setData(addedBatteries);
            response.setStatus(HttpStatus.OK);
        } catch(IllegalArgumentException e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @Override
    public ResponseEntity<BatteryCapacityResponse> getBatteriesWithinPostcodeRange(Integer postcodeStart, Integer postcodeEnd) {
        BatteryCapacityResponse response = new BatteryCapacityResponse();

        try {
            BatteryCapacityResponseDTO batteriesWithinRange =
                    service.getBatteriesWithinPostcodeRange(postcodeStart, postcodeEnd);

            response.setMessage("Batteries within postcode range fetched successfully");
            response.setData(batteriesWithinRange);
            response.setStatus(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
