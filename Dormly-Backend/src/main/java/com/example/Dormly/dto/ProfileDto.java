package com.example.Dormly.dto;


import lombok.*;

import java.net.URL;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ProfileDto {

    private URL image;
    private String bio;
    private String location;
    private String email;
    private String firstname;
    private String lastname;
    private List<URL> userListings;
}
