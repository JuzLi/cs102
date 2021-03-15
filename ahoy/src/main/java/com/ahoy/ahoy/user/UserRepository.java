package com.ahoy.ahoy.user;

import com.ahoy.ahoy.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("Select u from User u where u.username = :name")
    User findByUsername(@Param("name") String name);
}
