package com.ahoy.ahoy.alert;

import com.ahoy.ahoy.alert.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Integer> {
}
