package com.adps.energy_provider.utils;

import com.adps.energy_provider.dto.BatteryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BatteriesResponse extends BaseResponse {
    List<BatteryDTO> data;
}
