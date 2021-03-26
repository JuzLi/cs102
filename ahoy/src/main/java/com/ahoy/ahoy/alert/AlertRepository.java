package com.ahoy.ahoy.alert;

import com.ahoy.ahoy.alert.Alert;
import com.ahoy.ahoy.voyage.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Integer> {
    @Query("select count(a) from Alert a where a.voyage = :voyage and a.alertPK.alerttype = :alertType")
    public int countAlertsOfVoyageAndType(@Param("voyage") Voyage voyage, @Param("alertType") String alertType);

    @Query("select distinct a.alertPK.alerttype from Alert a")
    public List<String> allAlertType();

}
