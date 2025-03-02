package com.example.Dormly.repository;

import com.example.Dormly.entity.Chat;
import com.example.Dormly.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.security.Principal;
import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {


    @Query("SELECT c from Chat c where c.buyer.user.email=?1 AND c.seller=?2 AND c.listing.id=?3")
    List<Chat> findChatsByListingAndUsers(String buyer, Profile seller, Long listingId);


    @Query("SELECT c from Chat c where c.buyer=?1 OR c.seller=?1")
    List<Chat> findUserInbox(String userEmail);
}
