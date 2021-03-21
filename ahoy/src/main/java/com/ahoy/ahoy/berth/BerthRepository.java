package com.ahoy.ahoy.berth;

import com.ahoy.ahoy.berth.Berth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BerthRepository extends JpaRepository<Berth, Integer> {
    @Query("Select b from Berth b where b.berthnum = :berthnum")
    Berth findByBerthNum(@Param("berthnum") String num);
}
