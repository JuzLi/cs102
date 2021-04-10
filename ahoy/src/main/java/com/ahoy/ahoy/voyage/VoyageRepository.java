package com.ahoy.ahoy.voyage;

import com.ahoy.ahoy.berth.Berth;
import com.ahoy.ahoy.vessel.Vessel;
import com.ahoy.ahoy.voyage.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Integer> {

    @Query(value = "Select * from voyage where btrdt between :dateFrom and :dateTo", nativeQuery = true)
    List<Voyage> retrieveVoyagesBetweenDates(@Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo);

    @Query(value = "Select * from voyage where btrdt > :dateFrom", nativeQuery = true)
    List<Voyage> retrieveVoyagesFromDates(@Param("dateFrom") String dateFrom);

    @Query("Select v from Voyage v where v.voyagePK.abbrvslm = :abbrvslm and v.voyagePK.invoyn = :invoyn")
    public Voyage findByPrimarykey(@Param("abbrvslm") String abbrvslm, @Param("invoyn") String invoyn);

    @Query("Select v from Voyage v where v.vessel = :vessel")
    List<Voyage> findByVessel(@Param("vessel") Vessel vessel);

    @Transactional
    @Modifying
    @Query("update Voyage v set v.btrdt = :updateBtr where v.voyagePK = :voyagePK")
    public void updateBtrdt(@Param("voyagePK") VoyagePK voyagePK, @Param("updateBtr") String updateBtr);

    @Transactional
    @Modifying
    @Query("update Voyage v set v.unbthgdt = :updateUnbthgdt where v.voyagePK = :voyagePK")
    public void updateUnbthgdt(@Param("voyagePK") VoyagePK voyagePK, @Param("updateUnbthgdt") String updateUnbthgdt);

    @Transactional
    @Modifying
    @Query("update Voyage v set v.berth = :updateBerth where v.voyagePK = :voyagePK")
    public void updateBerth(@Param("voyagePK") VoyagePK voyagePK, @Param("updateBerth") Berth berth);

    @Transactional
    @Modifying
    @Query("update Voyage v set v.status = :updateStatus where v.voyagePK = :voyagePK")
    public void updateStatus(@Param("voyagePK") VoyagePK voyagePK, @Param("updateStatus") String status);
}
