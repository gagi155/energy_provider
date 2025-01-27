package com.adps.energy_provider.repository;

import com.adps.energy_provider.model.Battery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BatteryRepository extends JpaRepository<Battery, Long> {
    @Query("SELECT b FROM Battery b WHERE (:postcodeStart IS NULL OR b.postcode >= :postcodeStart) " +
            "AND (:postcodeEnd IS NULL OR b.postcode <= :postcodeEnd) " +
            "ORDER BY b.name ASC")
    List<Battery> findBatteriesWithinRangeAsc(@Param("postcodeStart") Integer postcodeStart,
                                           @Param("postcodeEnd") Integer postcodeEnd);

    @Query("SELECT b FROM Battery b WHERE (:postcodeStart IS NULL OR b.postcode >= :postcodeStart) " +
            "AND (:postcodeEnd IS NULL OR b.postcode <= :postcodeEnd) " +
            "ORDER BY b.name DESC")
    List<Battery> findBatteriesWithinRangeDesc(@Param("postcodeStart") Integer postcodeStart,
                                               @Param("postcodeEnd") Integer postcodeEnd);

}
