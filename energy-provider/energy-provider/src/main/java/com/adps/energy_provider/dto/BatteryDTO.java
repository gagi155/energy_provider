package com.adps.energy_provider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BatteryDTO {
    private Long id;
    private String name;
    private Integer postcode;
    private Integer wattCapacity;
}
