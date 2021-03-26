package com.ahoy.ahoy.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertPreferenceRepository extends JpaRepository<AlertPreference, Integer> {
}
