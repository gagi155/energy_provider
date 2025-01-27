package com.adps.energy_provider.mapper;

import com.adps.energy_provider.dto.BatteryCreateDTO;
import com.adps.energy_provider.dto.BatteryDTO;
import com.adps.energy_provider.model.Battery;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BatteryMapper {

    BatteryDTO batteryToBatteryDTO(Battery battery);
    Battery batteryCreateDTOToBattery(BatteryCreateDTO batteryDTO);
}
