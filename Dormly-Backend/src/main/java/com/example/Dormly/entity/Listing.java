package com.example.Dormly.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name="profile_id", nullable = false)
    private Profile profile;

    @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL)
    private List<Photos> photos = new ArrayList<>();


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

    @Column(name = "is_sold", nullable = false)
    private boolean isSold;

    private String brand;

    @Column(name = "listed_date", nullable = false)
    //set automatically by us
    private Date ListedDate;

    @Column(nullable = false)
    private String listingImageURL;

    private String category;


    @Column(name = "updated_at")
    //this will be set in the constructor, any persistence to the db will be seen as an update
    //i.e user may change listing information, may delete a listing or may create one
    private String updatedAt;








}
