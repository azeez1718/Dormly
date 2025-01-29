package com.example.Dormly.dto;


import lombok.*;

import java.net.URL;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfileDto {

    private URL image;
    private String bio;
    private String location;
    private String email;
    private String firstname;
    private String lastname;
}
