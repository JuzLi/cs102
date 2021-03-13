package com.ahoy.ahoy.repo;

import com.ahoy.ahoy.voyage.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Integer> {

    @Query("Select v from Voyage v where v.voyageID = :id")
    public Voyage findByVoyageID(@Param("id") int num);

    @Query("Select v from Voyage v where v.vessel.abbrvslm = :abbrvslm and v.invoyn = :invoyn")
    public Voyage findByPrimarykey(@Param("abbrvslm") String abbrvslm, @Param("invoyn") String invoyn);
}
