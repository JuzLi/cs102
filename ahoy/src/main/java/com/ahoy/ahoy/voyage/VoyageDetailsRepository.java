package com.ahoy.ahoy.voyage;

import com.ahoy.ahoy.voyage.Voyage;
import com.ahoy.ahoy.voyage.VoyageDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoyageDetailsRepository extends JpaRepository<VoyageDetails,Integer> {

    @Query("select count(v.voyageDetailsPK.voyageDetailsID) from VoyageDetails v where v.voyage = :voyage")
    public int countAlertsOfVoyage(@Param("voyage") Voyage voyage);

    @Query("select v from VoyageDetails v where v.voyage = :voyage and v.voyageDetailsPK.voyageDetailsID = :count")
    public VoyageDetails latestDetails(@Param("voyage") Voyage voyage, @Param("count") int count);
}
