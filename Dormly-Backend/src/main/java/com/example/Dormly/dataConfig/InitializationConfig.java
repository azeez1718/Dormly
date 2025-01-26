package com.example.Dormly.dataConfig;


import com.example.Dormly.models.Profile;
import com.example.Dormly.repository.ProfileRepository;
import com.example.Dormly.repository.UserRepository;
import com.example.Dormly.security.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class InitializationConfig {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Bean
    CommandLineRunner commandLineRunner(ProfileRepository profileRepository,
                                        UserRepository userRepository){
        return args -> {

            //find the first user in the database and set the user field of the profile entity

            Users user  = userRepository.findAll()
                    .stream()
                    .findFirst()
                    .orElseThrow(()-> new UsernameNotFoundException("user does not exist"));


            //default location will be set to the university location
            Profile p1 = new Profile("image.png","Computer Engineer at QMUL 25'", "Queen Mary University",
                    user);

            profileRepository.save(p1);



        };
    }



}
