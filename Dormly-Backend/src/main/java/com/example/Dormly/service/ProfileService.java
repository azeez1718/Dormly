package com.example.Dormly.service;

import com.example.Dormly.dto.ProfileDto;
import com.example.Dormly.models.Profile;
import com.example.Dormly.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    public ProfileDto fetchProfile(String userEmail) {
        Optional<Profile> userProfile = profileRepository.findByEmail(userEmail);

        if(userProfile.isEmpty())

    }
}
