package com.example.Dormly.initialization;


import com.example.Dormly.constants.Role;
import com.example.Dormly.entity.Category;
import com.example.Dormly.entity.Profile;
import com.example.Dormly.jwt.model.Users;
import com.example.Dormly.repository.CategoryRepository;
import com.example.Dormly.repository.ProfileRepository;
import com.example.Dormly.repository.UserRepository;
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
    private final CategoryRepository categoryRepository;


    @Bean
    CommandLineRunner commandLineRunner(ProfileRepository profileRepository,
                                        UserRepository userRepository, CategoryRepository categoryRepository) {
        return args -> {
//
//            //find the first user in the database and set the user field of the profile entity
//
//            Users user = new Users("abas", "jama", "abas@qmul.ac.uk", List.of(Role.USER));
//            user.setPassword(passwordEncoder.encode("abasjama"));
//            userRepository.save(user);
//
//            Profile p1 = Profile.builder()
//                    .bio("Computer Engineer at QMUL 25")
//                    .Location("London, kensington")
//                    .user(user)
//                    .build();
//            profileRepository.save(p1);
//
//    Category Dorm = Category
//            .builder()
//            .name("Dorm Essentials")
//            .build();
//    Category clothes = Category
//            .builder()
//            .name("Clothing & Accessories")
//            .build();
//
//    Category electronics = Category
//            .builder()
//            .name("Electronics")
//            .build();
//
//    Category sports = Category
//            .builder()
//            .name("Sports & Fitness")
//            .build();
//
//    Category School = Category
//            .builder()
//            .name("Textbooks & Resources")
//            .build();
//
//    Category music = Category
//            .builder()
//            .name("Musical Instruments")
//            .build();
//
//    Category garden = Category
//            .builder()
//            .name("Garden and Outdoors")
//            .build();
//
//    Category other = Category
//            .builder()
//            .name("other")
//            .build();
//
//
//    /// initial seed data for our categories which are predefined before a user creates a listing
//    categoryRepository.saveAll(List.of(Dorm, clothes, electronics, sports, School,garden, music, other));





        };
    }



}
