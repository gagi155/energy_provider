package com.adps.energy_provider.battery;

import com.adps.energy_provider.config.Config;
import com.adps.energy_provider.dto.BatteryCapacityResponseDTO;
import com.adps.energy_provider.dto.BatteryCreateDTO;
import com.adps.energy_provider.dto.BatteryDTO;
import com.adps.energy_provider.mapper.BatteryMapper;
import com.adps.energy_provider.model.Battery;
import com.adps.energy_provider.repository.BatteryRepository;
import com.adps.energy_provider.service.impl.BatteryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BatteryServiceTest {
    @Mock
    private BatteryRepository repository;

    @Mock
    private BatteryMapper mapper;

    @Mock
    private Config config;

    @InjectMocks
    private BatteryServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenAddBatteries_thenAddBatteries(){
        //Arrange
        List<BatteryCreateDTO> batteryCreateDTOs = List.of(
                new BatteryCreateDTO("AA1", 1000, 500),
                new BatteryCreateDTO("BB1", 2000, 600)
        );

        List<Battery> batteries = List.of(
                new Battery(1L, "AA1", 1000, 500),
                new Battery(2L, "BB2", 2000, 600)
        );

        when(mapper.batteryCreateDTOToBattery(batteryCreateDTOs.get(0))).thenReturn(batteries.get(0));
        when(mapper.batteryCreateDTOToBattery(batteryCreateDTOs.get(1))).thenReturn(batteries.get(1));
        when(repository.saveAll(anyList())).thenReturn(batteries);
        when(mapper.batteryToBatteryDTO(batteries.get(0))).thenReturn(new BatteryDTO(1L, "AA1", 1000, 500));
        when(mapper.batteryToBatteryDTO(batteries.get(1))).thenReturn(new BatteryDTO(2L, "BB1", 2000, 600));

        //Act
        List<BatteryDTO> result = service.addBatteries(batteryCreateDTOs);

        // Assert
        assertEquals(2, result.size());
        assertEquals("AA1", result.get(0).getName());
        assertEquals("BB1", result.get(1).getName());

        verify(repository).saveAll(anyList());
        verify(mapper, times(2)).batteryCreateDTOToBattery(any());
        verify(mapper, times(2)).batteryToBatteryDTO(any());
    }

    @Test
    void whenGetBatteriesWithinPostcodeRangeAsc_thenReturnBatteriesAsc() {
        // Arrange
        when(config.getSortingOrder()).thenReturn("ASC");

        List<Battery> batteries = Arrays.asList(
                new Battery(1L, "AA1", 2000, 500),
                new Battery(2L, "BB1", 2005, 300)
        );
        when(repository.findBatteriesWithinRangeAsc(2000, 2010)).thenReturn(batteries);

        // Act
        BatteryCapacityResponseDTO result = service.getBatteriesWithinPostcodeRange(2000, 2010);

        // Assert
        assertEquals(Arrays.asList("AA1", "BB1"), result.getNames());
        assertEquals(400.0, result.getAverageWattCapacity());
        assertEquals(800L, result.getTotalWattCapacity());

        verify(repository, times(1)).findBatteriesWithinRangeAsc(2000, 2010);
        verify(repository, never()).findBatteriesWithinRangeDesc(2000, 2010);
    }

    @Test
    void whenGetBatteriesWithinPostcodeRangeDesc_thenReturnBatteriesDesc() {
        //Arrange
        when(config.getSortingOrder()).thenReturn("DESC");

        List<Battery> batteries = Arrays.asList(
                new Battery(1L, "BB1", 2000, 500),
                new Battery(2L, "AA1", 2005, 300)
        );
        when(repository.findBatteriesWithinRangeDesc(2000, 2010)).thenReturn(batteries);

        // Act
        BatteryCapacityResponseDTO result = service.getBatteriesWithinPostcodeRange(2000, 2010);

        // Assert
        assertEquals(Arrays.asList("BB1", "AA1"), result.getNames());
        assertEquals(400.0, result.getAverageWattCapacity());
        assertEquals(800L, result.getTotalWattCapacity());

        verify(repository, times(1)).findBatteriesWithinRangeDesc(2000, 2010);
        verify(repository, never()).findBatteriesWithinRangeAsc(2000, 2010);
    }

    @Test
    void whenGetBatteriesWithinPostcodeRangeEmpty_thenReturnEmpty() {
        //Arrange
        when(config.getSortingOrder()).thenReturn("ASC");
        when(repository.findBatteriesWithinRangeAsc(2000, 2010)).thenReturn(List.of());

        //Act&Assert
        assertThrows(NoSuchElementException.class, () -> {
            service.getBatteriesWithinPostcodeRange(2000, 2010);
        });

    }

}
