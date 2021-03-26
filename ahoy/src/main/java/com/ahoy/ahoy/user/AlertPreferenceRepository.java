package com.ahoy.ahoy.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AlertPreferenceRepository extends JpaRepository<AlertPreference, Integer> {

    @Query("select distinct a.alerttype from AlertPreference a where a.user = :user")
    public List<String> allSubscribedAlertTypes(@Param("user") User user);

    @Transactional
    @Modifying
    @Query("delete from AlertPreference a where a.user = :user and a.alerttype = :type")
    public void deleteAlertPreference(@Param("user") User user, @Param("type") String type);
}
