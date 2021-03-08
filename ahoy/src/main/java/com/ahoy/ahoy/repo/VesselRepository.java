package com.ahoy.ahoy.repo;

import com.ahoy.ahoy.vessel.Vessel;
import com.google.gson.JsonArray;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VesselRepository extends JpaRepository <Vessel,Integer> {

    @Query("Select v from Vessel v where v.fullName = :name")
    List<Vessel> findByFullName(@Param("name") String name);
}
