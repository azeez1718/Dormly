package com.example.Dormly.entity;


import com.example.Dormly.security.model.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Listing> listings = new ArrayList<>();

    public Profile(String profilePicture, String bio, String location, Users user) {
        this.profilePicture = profilePicture;
        this.bio = bio;
        Location = location;
        this.user = user;
    }
}
