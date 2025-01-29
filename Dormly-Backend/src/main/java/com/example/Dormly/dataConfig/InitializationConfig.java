package com.example.Dormly.dataConfig;


import com.example.Dormly.aws.S3Service;
import com.example.Dormly.constants.Role;
import com.example.Dormly.entity.Profile;
import com.example.Dormly.repository.ProfileRepository;
import com.example.Dormly.repository.UserRepository;
import com.example.Dormly.jwt.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final S3Service s3;
    @Value("{aws.bucket}")
    private String bucket;

    @Bean
    CommandLineRunner commandLineRunner(ProfileRepository profileRepository,
                                        UserRepository userRepository, S3Service s3){
        return args -> {

            //find the first user in the database and set the user field of the profile entity

            Users user = new Users("abas", "jama", "abas@qmul.ac.uk", List.of(Role.USER));
            user.setPassword(passwordEncoder.encode("abasjama"));
            userRepository.save(user);

            Profile p1 = Profile.builder()
                    .bio("Computer Engineer at QMUL 25")
                    .Location("London, kensington")
                    .user(user)
                    .build();
            profileRepository.save(p1);


//            s3.putObject(bucket, "test", "hello".getBytes());
//
//            //allows us to get the image stored in the bucket with its unique identifier 'test'
//            byte[] bytes = s3.getObject(bucket, "test");
//
//            System.out.println("horray " + new String(bytes));

        };
    }



}
