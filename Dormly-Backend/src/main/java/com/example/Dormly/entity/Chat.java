package com.example.Dormly.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Chat {
    /// the output message acts as our DTO

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Profile sellerId;


    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private Profile buyerId;

    @Column(nullable = false)
    private String content;

    /// we can persist the listing id as a foreign key
    /// helps with data intergirty and makes it easier to query the listings

    @ManyToOne
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listing;

    @Column(name = "updated_at", nullable = false)
    private String updatedAt;
}
