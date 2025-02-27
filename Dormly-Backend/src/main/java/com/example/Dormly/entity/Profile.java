package com.example.Dormly.entity;


import com.example.Dormly.jwt.model.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "profile_picture_id", unique = true, nullable = true)
    private String profilePictureId;

    @Column(name = "bio", nullable = true)
    private String bio;

    //default, will be set to university Location
    private String Location;


    private String university = "queens mary university";

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Listing> listings = new ArrayList<>();


    /// whenever the user writes profile.getOrdersReceived,
    /// we return all the people that have made an order on their listings
    @OneToMany(mappedBy = "sellerId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> ordersReceived = new ArrayList<>();

    @OneToMany(mappedBy = "buyerId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> ordersPlaced = new ArrayList<>();











}
