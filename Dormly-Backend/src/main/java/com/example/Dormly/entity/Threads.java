package com.example.Dormly.entity;

import aj.org.objectweb.asm.commons.Remapper;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Threads {
    ///This thread represent the distinct conversation between two users

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "thread_seq")
    @SequenceGenerator(name = "thread_seq", sequenceName = "thread_seq", allocationSize = 1)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Profile seller;


    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private Profile buyer;

    /// we can persist the listing id as a foreign key
    /// helps with data integrity and makes it easier to query the listings
    @ManyToOne
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listing;


    /// bidirectinal with messages, which allows us to fetch the messages from a thread
    @OneToMany(mappedBy = "thread", fetch = FetchType.EAGER)
    @OrderBy("timestamp ASC")
    @JsonManagedReference
    @Nullable  /// when users have no messages exchanged this can be null
    private List<Message> messages;

    //allow users to soft delete listings
    @Column(name ="is_deleted", nullable = false)
    private Boolean isDeleted;



}
