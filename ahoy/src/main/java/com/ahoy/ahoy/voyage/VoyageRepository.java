package com.ahoy.ahoy.voyage;

import com.ahoy.ahoy.vessel.Vessel;
import com.ahoy.ahoy.voyage.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Integer> {

    @Query(value = "Select * from voyage where btrdt between :dateFrom and :dateTo", nativeQuery = true)
    List<Voyage> retrieveVoyagesBetweenDates(@Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo);

    @Query("Select v from Voyage v where v.voyagePK.abbrvslm = :abbrvslm and v.voyagePK.invoyn = :invoyn")
    public Voyage findByPrimarykey(@Param("abbrvslm") String abbrvslm, @Param("invoyn") String invoyn);
}
