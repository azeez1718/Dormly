package com.example.Dormly.dto;


import com.example.Dormly.entity.Profile;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URL;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDto {

    private Long id;
    private URL image;
    private String bio;
    private String location;
    private String email;
    private String firstname;
    private String lastname;
    //private List<URL> userListings;
    private List<ListingDtoResponse> profileListings;


    /// the chat uses this dto to convert the profile object into a DTO to return the seller and buyer info

    public static ProfileDto from(Profile profile) {
        return ProfileDto.builder()
                .id(profile.getId())
                .email(profile.getUser().getEmail())
                .firstname(profile.getUser().getFirstname())
                .lastname(profile.getUser().getLastname())

                .build();


    }
}
