package com.example.Dormly.repository;

import com.example.Dormly.security.model.Users;
import org.apache.catalina.UserDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {



    Optional<UserDetails> findByEmail(@Param("email")String username);
}
