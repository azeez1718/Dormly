package com.example.Dormly.service;


import com.example.Dormly.aws.PreSignedUrlService;
import com.example.Dormly.dto.ChatDto;
import com.example.Dormly.entity.Chat;
import com.example.Dormly.entity.Listing;
import com.example.Dormly.entity.Profile;
import com.example.Dormly.exceptions.ListingNotFoundException;
import com.example.Dormly.repository.ChatRepository;
import com.example.Dormly.repository.ListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.security.Principal;
import java.util.List;
@RequiredArgsConstructor
@Service
public class ChatService {
    /// we use this service to persist messages and render them to the user if the user received it when
    ///they were not connected to the websocket

    private final ChatRepository chatRepository;
    private final ListingRepository listingRepository;
    private final PreSignedUrlService preSignedUrlService;
    ///whenever a user clicks on 'message seller' ideally what we want is to check if these two users have a conversation
    /// about that specific listing, if so we return the associated chat object including the content.
    /// we want to persist the images in order of their created date

    public List<ChatDto> findChatHistory(Principal principal, Long listingId) {
        Listing listing  = listingRepository.findById(listingId)
                .orElseThrow(()->new ListingNotFoundException("listing id does not exist"));

        /// get the owner of the listing
        Profile profile = listing.getProfile();

        /// ensure the user is not trying to message himself as the seller
        if(principal.getName().equals(profile.getUser().getEmail())){
            throw new RuntimeException("users can not message themselves from the listings page");
        }

        /// we return a dto of the chat, to prevent serializing sensitive information
        List<ChatDto> chats = chatRepository.findAll()
                .stream()
                .filter(chat -> chat.getBuyer().getUser().getEmail().equals(principal.getName())
                        && chat.getSeller().equals(profile))
                .filter(chat -> chat.getListing().getId().equals(listingId))
                .filter(chat->chat.getCreatedAt().get)
                .map(ChatDto::convertToDto)
                .toList();

        /// because this is a list of chats associated with the same user, we dont need to call the presigned service for each dto
        /// we can set the profile pictures of the user for only the first chat dto, and leave the rest null
        /// the principal is the buyer here and the profile is the user

        chats.get(0).getBuyer().setImage(preSignedUrlService.generateProfilePreSignedUrlByEmail(principal.getName()));
        chats.get(0).getSeller().setImage(preSignedUrlService.generateProfilePreSignedUrlByEmail(profile.getUser().getEmail()));


        return chats;












    }
}
