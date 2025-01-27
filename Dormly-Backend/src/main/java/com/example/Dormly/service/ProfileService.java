package com.example.Dormly.service;

import com.example.Dormly.dto.ProfileDto;
import com.example.Dormly.exceptions.ProfileNotFoundException;
import com.example.Dormly.entity.Profile;
import com.example.Dormly.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    public ProfileDto fetchProfile(String userEmail) {
        Optional<Profile> userProfile = profileRepository.findByEmail(userEmail);

        if(userProfile.isEmpty()){
            throw new ProfileNotFoundException("This user does not exist");
        }
        //if the user has a valid profile, we can then also get their email and firstname to return back to the client

        Profile profile = userProfile.get();


        /**
         * convert the profile object we return to a DTO to hide internals
         */
        ProfileDto profileDto = new ProfileDto(
                profile.getProfilePicture(),
                profile.getBio(),
                profile.getLocation(),
                profile.getUser().getEmail(),
                profile.getUser().getFirstname(),
                profile.getUser().getLastname()

        );

        return profileDto;

    }
}
