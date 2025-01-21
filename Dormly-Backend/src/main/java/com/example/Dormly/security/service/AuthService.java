package com.example.Dormly.security.service;

import com.example.Dormly.constants.Role;
import com.example.Dormly.repository.UserRepository;
import com.example.Dormly.security.dto.RegisterDto;
import com.example.Dormly.security.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public String giveToken(RegisterDto registerDto) {
        /**
         * ensure the email is a valid university email by checking the domain name as Queens mary University
         */

        Optional<UserDetails> checkUserExists = userRepository.findByEmail(registerDto.getEmail());

        if(checkUserExists.isPresent() && !registerDto.getEmail().contains("@qmul.ac.uk")){
            throw new IllegalArgumentException("please check the entered credentials again");
        }

        /**
         *if the user does not exist, convert the registerDto to a userDetails object and save the user
         *hash the incoming password and set a default role for the user
         */

        Users user = new Users(
                registerDto.getFirstname(),
                registerDto.getLastname(),
                registerDto.getEmail()
        );

        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(Role.USER);






    }
}
