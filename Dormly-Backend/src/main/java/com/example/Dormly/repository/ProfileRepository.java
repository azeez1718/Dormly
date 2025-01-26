package com.example.Dormly.repository;

import com.example.Dormly.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
