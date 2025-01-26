package com.example.Dormly.repository;

import com.example.Dormly.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {


    @Query("SELECT p FROM Profile p where p.user.email=:email")
    Optional<Profile> findByEmail(@Param("email") String email);
}
