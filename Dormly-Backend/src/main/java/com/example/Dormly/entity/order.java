package com.example.Dormly.entity;


import com.example.Dormly.constants.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    @Column(name = "order_number", nullable = false)
    private UUID orderNumber;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;


    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "buyer_id", nullable = false)
    private Profile buyerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "sellerId", nullable = false)
    private Profile sellerId;

    @OneToMany
    @JsonBackReference
    @JoinColumn(name = "listing_id")
    private List<Listing> listings = new ArrayList<>();




    @Column(name = "updated_at", nullable = false)
    private LocalDate updateDate;





}
