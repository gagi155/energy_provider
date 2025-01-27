package com.adps.energy_provider.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BatteryCreateDTO {
    @NotNull
    private String name;

    @NotNull
    private Integer postcode;

    @NotNull
    @Min(1)
    private Integer wattCapacity;
}
