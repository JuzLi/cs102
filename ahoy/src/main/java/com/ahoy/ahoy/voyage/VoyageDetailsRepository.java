package com.ahoy.ahoy.voyage;

import com.ahoy.ahoy.voyage.Voyage;
import com.ahoy.ahoy.voyage.VoyageDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoyageDetailsRepository extends JpaRepository<VoyageDetails,Integer> {

    @Query("select count(v) from VoyageDetails v where v.voyage = :voyage")
    public int countDetailsOfVoyage(@Param("voyage") Voyage voyage);

    @Query("select v from VoyageDetails v where v.voyage = :voyage and v.voyageDetailsPK.voyageDetailsID = :count")
    public VoyageDetails findDetails(@Param("voyage") Voyage voyage, @Param("count") int count);

//    @Query(value = "select v from VoyageDetails v where v.abbrvslm = :voyage order by v.voyageDetailsPK.voyageDetailsID desc limit 1", nativeQuery = true)
//    public VoyageDetails findOneDetail(@Param("voyage") Voyage voyage);

//    @Query(value = "select * from voyage v inner join voyagedetails vd on v.abbrvslm = vd.abbrvslm where v.abbrvslm = voyage.get )

    @Query("select vd from VoyageDetails vd where vd.voyage = :voyage order by vd.voyageDetailsPK.voyageDetailsID desc")
    public List<VoyageDetails> findOneDetail(@Param("voyage") Voyage voyage);
}
