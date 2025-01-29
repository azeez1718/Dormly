package com.example.Dormly.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfileDto {

    private byte[] image;
    private String bio;
    private String location;
    private String email;
    private String firstname;
    private String lastname;
}
