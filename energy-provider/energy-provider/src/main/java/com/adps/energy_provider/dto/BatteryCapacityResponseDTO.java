package com.adps.energy_provider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BatteryCapacityResponseDTO {
    private List<String> names;
    private double averageWattCapacity;
    private Long totalWattCapacity;
}
