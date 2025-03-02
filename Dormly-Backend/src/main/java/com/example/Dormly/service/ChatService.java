package com.example.Dormly.service;


import com.example.Dormly.aws.PreSignedUrlService;
import com.example.Dormly.dto.ChatDto;
import com.example.Dormly.entity.Chat;
import com.example.Dormly.entity.Listing;
import com.example.Dormly.entity.Profile;
import com.example.Dormly.exceptions.ListingNotFoundException;
import com.example.Dormly.exceptions.ProfileNotFoundException;
import com.example.Dormly.repository.ChatRepository;
import com.example.Dormly.repository.ListingRepository;
import com.example.Dormly.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatService {
    /// we use this service to persist messages and render them to the user if the user received it when
    ///they were not connected to the websocket

    private final ChatRepository chatRepository;
    private final ListingRepository listingRepository;
    private final PreSignedUrlService preSignedUrlService;
    private final ProfileRepository profileRepository;

    ///whenever a user clicks on 'message seller' ideally what we want is to check if these two users have a conversation
    /// about that specific listing, if so we return the associated chat object including the content.
    /// we want to persist the images in order of their created date

    public List<ChatDto> findPreviousChatForListing(String buyer, Long listingId) {
        Listing listing  = listingRepository.findById(listingId)
                .orElseThrow(()->new ListingNotFoundException("listing id does not exist"));

        /// get the owner of the listing
        Profile seller = listing.getProfile();

        /// ensure the user is not trying to message himself as the seller
        if(buyer.equals(seller.getUser().getEmail())){
            System.out.println(seller.getUser().getEmail());
            System.out.println(buyer);
            throw new RuntimeException("users can not message themselves from the listings page");
        }

        /// query ensures both users have a chat history about this specific listing, if not we throw an error
        List<Chat> findChatAssociatedWithListingAndUsers = chatRepository.findChatsByListingAndUsers(buyer, seller, listingId);

        /// we return a dto of chats, and ensure they are in order of oldest to newest
        List<ChatDto> chats = findChatAssociatedWithListingAndUsers
                .stream()
                .sorted(Comparator.comparing(Chat::getCreatedAt))
                .map(ChatDto::convertToDto)
                .collect(Collectors.toList());

        /// because this is a list of chats associated with the same user, we don't need to call the presigned service for each dto
        /// we can set the profile pictures of the user for only the first chat dto, and leave the rest null
        /// the buyer is the principal(the authenticated user) and the seller is the profile instance of the seller

        chats.get(0).getBuyer().setImage(preSignedUrlService.generateProfilePreSignedUrlByEmail(buyer));
        chats.get(0).getSeller().setImage(preSignedUrlService.generateProfilePreSignedUrlByEmail(seller.getUser().getEmail()));
        return chats;
    }

    /// retrieve all the profiles users have in their inbox,
    ///for each chat id we check if the user is either a seller or a buyer
    /// this way we know what conversations the users had
    public void FindUserChats(String userEmail){
        Profile profile = profileRepository.findByEmail(userEmail)
                .orElseThrow(()->new ProfileNotFoundException("profile does not exist"));

        List<Chat> findUserChats = chatRepository.findUserInbox(userEmail);
        /**
         *  for each chat we need to return the profile of the users,each chat between two users always has a unique listing id
         */




    }
}
