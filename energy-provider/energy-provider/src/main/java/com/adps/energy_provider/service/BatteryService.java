package com.adps.energy_provider.service;

import com.adps.energy_provider.dto.BatteryCapacityResponseDTO;
import com.adps.energy_provider.dto.BatteryCreateDTO;
import com.adps.energy_provider.dto.BatteryDTO;

import java.util.List;

public interface BatteryService {
    List<BatteryDTO> addBatteries(List<BatteryCreateDTO> batteries);

    BatteryCapacityResponseDTO getBatteriesWithinPostcodeRange(Integer postcodeStart, Integer postcodeEnd);
}
