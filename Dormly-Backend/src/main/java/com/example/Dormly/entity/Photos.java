package com.example.Dormly.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Photos {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "photo_url", nullable = false)
    private String url;


    //Listing items will be stored using Amazon storage service provided by AWS


    /**
     * Listing and photos have
     */

    @ManyToOne
    @JoinColumn(name = "listing_id")
    @JsonIgnore
    private Listing listing;
}
