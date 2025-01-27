package com.example.Dormly.dataConfig;


import com.example.Dormly.constants.Role;
import com.example.Dormly.entity.Profile;
import com.example.Dormly.repository.ProfileRepository;
import com.example.Dormly.repository.UserRepository;
import com.example.Dormly.security.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class InitializationConfig {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner commandLineRunner(ProfileRepository profileRepository,
                                        UserRepository userRepository){
        return args -> {

            //find the first user in the database and set the user field of the profile entity

            Users user = new Users("abas", "jama", "abas@qmul.ac.uk", List.of(Role.USER));
            user.setPassword(passwordEncoder.encode("abasjama"));
            userRepository.save(user);

            //default location will be set to the university location
            Profile p1 = new Profile("image.png","Computer Engineer at QMUL 25'", "Queen Mary University",
                    user);

            profileRepository.save(p1);



        };
    }



}
