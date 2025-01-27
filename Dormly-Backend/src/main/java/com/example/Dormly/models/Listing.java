package com.example.Dormly.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;

    @ManyToOne
    @JoinColumn(name="profile_id", nullable = false)
    private Profile profile;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(nullable = false)
    private String condition;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    //one stock item or plenty of stock
    private String availability;

    @Column(nullable = false)
    private String Location;

    private String brand;

    @Column(name = "listed_date", nullable = false)
    //set automatically by us
    private Date ListedDate;


    @Column(name = "updated_at")
    //this will be set in the constructor, any persistence to the db will be seen as an update
    //i.e user may change listing information, may delete a listing or may create one
    private String updatedAt;








}
