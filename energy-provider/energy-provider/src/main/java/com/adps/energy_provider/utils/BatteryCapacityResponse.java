package com.adps.energy_provider.utils;

import com.adps.energy_provider.dto.BatteryCapacityResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BatteryCapacityResponse extends  BaseResponse{
    BatteryCapacityResponseDTO data;
}
