package com.example.Dormly.models;


import com.example.Dormly.security.model.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "profile_pic")
    private String profilePicture;

    private String bio;

    //default, will be set to university Location
    private String Location;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public Profile(String profilePicture, String bio, String location, Users user) {
        this.profilePicture = profilePicture;
        this.bio = bio;
        Location = location;
        this.user = user;
    }
}
