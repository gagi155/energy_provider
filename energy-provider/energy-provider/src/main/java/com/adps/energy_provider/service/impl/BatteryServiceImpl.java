package com.adps.energy_provider.service.impl;

import com.adps.energy_provider.config.Config;
import com.adps.energy_provider.dto.BatteryCapacityResponseDTO;
import com.adps.energy_provider.dto.BatteryCreateDTO;
import com.adps.energy_provider.dto.BatteryDTO;
import com.adps.energy_provider.mapper.BatteryMapper;
import com.adps.energy_provider.model.Battery;
import com.adps.energy_provider.repository.BatteryRepository;
import com.adps.energy_provider.service.BatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BatteryServiceImpl implements BatteryService {
    @Autowired
    private BatteryRepository repository;

    @Autowired
    private BatteryMapper mapper;

    @Autowired
    private Config config;

    @Override
    public List<BatteryDTO> addBatteries(List<BatteryCreateDTO> batteries) {
        if(batteries.isEmpty()){
            throw new IllegalArgumentException("No batteries to add");
        }

        List<Battery> batteryEntities = batteries.stream()
                .map(batteryCreateDTO -> mapper.batteryCreateDTOToBattery(batteryCreateDTO)).toList();

        List<Battery> savedBatteries = repository.saveAll(batteryEntities);

        return savedBatteries.stream()
                .map(battery -> mapper.batteryToBatteryDTO(battery)
                ).toList();
    }

    @Override
    public BatteryCapacityResponseDTO getBatteriesWithinPostcodeRange(Integer postcodeStart, Integer postcodeEnd) {
        String sortingOrder = config.getSortingOrder();

        List<Battery> batteries = "ASC".equalsIgnoreCase(sortingOrder)
                ? repository.findBatteriesWithinRangeAsc(postcodeStart, postcodeEnd)
                : repository.findBatteriesWithinRangeDesc(postcodeStart, postcodeEnd);

        if(batteries.isEmpty()){
            throw new NoSuchElementException("No batteries found in given postcode range");
        }

        List<String> batteryNames = batteries.stream()
                .map(Battery::getName)
                .toList();

        long totalWattCapacity = batteries.stream()
                .mapToLong(Battery::getWattCapacity)
                .sum();

        double averageWattCapacity = batteries.isEmpty() ? 0 : Math.round((totalWattCapacity / (double) batteries.size()) * 100.0) / 100.0;

        return new BatteryCapacityResponseDTO(batteryNames, averageWattCapacity, totalWattCapacity);
    }
}
