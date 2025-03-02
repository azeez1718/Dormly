package com.example.Dormly.initialization;



import com.example.Dormly.entity.Message;
import com.example.Dormly.entity.Profile;
import com.example.Dormly.entity.Threads;
import com.example.Dormly.exceptions.ListingNotFoundException;
import com.example.Dormly.exceptions.ProfileNotFoundException;
import com.example.Dormly.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class InitializationConfig {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;



    @Bean
    CommandLineRunner commandLineRunner(ProfileRepository profileRepository,
                                        UserRepository userRepository, CategoryRepository categoryRepository,
                                        MessageRepository messageRepository, ListingRepository listingRepository) {

        return args -> {


            /// abas and james begin conversing again over another listing
            /// this is to test whether the messages are returning from oldest to newest


//
//            /// lets create two default chat objects, between abas and james
            Profile abas = profileRepository.findById(1L).orElseThrow(()->new ProfileNotFoundException(""));
            Profile james = profileRepository.findById(2L).orElseThrow(()->new ProfileNotFoundException(""));

            //conversation between Abas and james, abas seller james buyer
            // for the same conversation there will be a few message objects
            // each object represents a sender and the message

            Message m1 = Message.builder()
                    .senderId(james)
                    .content("hi abas, could we negotiate the price down")
                    .timestamp(LocalDateTime.now())
                    .thread(Threads.builder()
                            .buyer(james)
                            .seller(abas)
                            .isDeleted(false)
                            .listing(listingRepository.findById(153L).orElseThrow(()->new ListingNotFoundException("")))
                            .build())
                    .build();

            /// it automatically persists the thread
            messageRepository.save(m1);




//
//            Profile profile = Profile.builder()
//                    .bio("Here to explore")
//                    .Location("mayfair")
//                    .user((Users)user)
//                    .build();

//            profileRepository.save(profile);
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
