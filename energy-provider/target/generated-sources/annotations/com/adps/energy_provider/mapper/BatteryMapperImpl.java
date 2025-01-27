package com.adps.energy_provider.mapper;

import com.adps.energy_provider.dto.BatteryCreateDTO;
import com.adps.energy_provider.dto.BatteryDTO;
import com.adps.energy_provider.model.Battery;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-27T18:13:32+0100",
    comments = "version: 1.6.2, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class BatteryMapperImpl implements BatteryMapper {

    @Override
    public BatteryDTO batteryToBatteryDTO(Battery battery) {
        if ( battery == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        Integer postcode = null;
        Integer wattCapacity = null;

        id = battery.getId();
        name = battery.getName();
        postcode = battery.getPostcode();
        wattCapacity = battery.getWattCapacity();

        BatteryDTO batteryDTO = new BatteryDTO( id, name, postcode, wattCapacity );

        return batteryDTO;
    }

    @Override
    public Battery batteryCreateDTOToBattery(BatteryCreateDTO batteryDTO) {
        if ( batteryDTO == null ) {
            return null;
        }

        Battery battery = new Battery();

        battery.setName( batteryDTO.getName() );
        battery.setPostcode( batteryDTO.getPostcode() );
        battery.setWattCapacity( batteryDTO.getWattCapacity() );

        return battery;
    }
}
