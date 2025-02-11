package com.example.Dormly.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "category_name",  nullable = false, unique = true)
    private String name;


    @OneToMany(mappedBy = "category")
    private List<Listing> listings;


}
