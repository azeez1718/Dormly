package com.example.Dormly.repository;


import com.example.Dormly.entity.Profile;
import com.example.Dormly.entity.Threads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ThreadsRepository extends JpaRepository<Threads, Long> {


    @Query("SELECT t from Threads t where t.buyer.user.email=?1 AND t.seller=?2 AND t.listing.id=?3")
    Optional<Threads> findChatsByListingAndUsers(String buyer, Profile seller, Long listingId);


    @Query("SELECT t from Threads t where t.buyer=?1 OR t.seller=?1")
    List<Threads> findUserInbox(Profile profile);
}
