package com.ahoy.ahoy.user;

import com.ahoy.ahoy.user.User;
import com.ahoy.ahoy.voyage.VoyagePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("Select u from User u where u.username = :name")
    User findByUsername(@Param("name") String name);

    @Query("Select u from User u where u.username = :name and u.email =:email")
    User findByUsernameAndEmail(@Param("name") String name, @Param("email") String email);

    @Transactional
    @Modifying
    @Query("update User u set u.email = :newEmail where u.username = :username")
    public void updateEmail(@Param("username") String username, @Param("newEmail") String newEmail);

    @Transactional
    @Modifying
    @Query("update User u set u.password = :newPass where u.username = :username")
    public void updatePassword(@Param("username") String username, @Param("newPass") String newPass);

    @Query("Select u from User u")
    public List<User> retrieveAllUsers();


}
