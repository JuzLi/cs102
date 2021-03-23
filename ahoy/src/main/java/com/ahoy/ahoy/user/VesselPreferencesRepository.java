package com.ahoy.ahoy.user;

import com.ahoy.ahoy.vessel.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VesselPreferencesRepository extends JpaRepository<VesselPreferences, Integer> {
    @Query("select vp from VesselPreferences vp where vp.user = :user and vp.vessel = :vessel")
    VesselPreferences findVesselPreferences(@Param("user") User user, @Param("vessel") Vessel vessel);

    @Query("select vp from VesselPreferences vp where vp.user = :user")
    List<VesselPreferences> allVesselPreferences(@Param("user") User user);
}
