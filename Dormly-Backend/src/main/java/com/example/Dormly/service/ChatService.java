package com.example.Dormly.service;


import com.example.Dormly.aws.PreSignedUrlService;
import com.example.Dormly.dto.MessageDto;
import com.example.Dormly.dto.ThreadsDto;
import com.example.Dormly.entity.Listing;
import com.example.Dormly.entity.Profile;
import com.example.Dormly.entity.Threads;
import com.example.Dormly.exceptions.ListingNotFoundException;
import com.example.Dormly.exceptions.ProfileNotFoundException;
import com.example.Dormly.exceptions.ThreadNotFoundException;
import com.example.Dormly.repository.ListingRepository;
import com.example.Dormly.repository.MessageRepository;
import com.example.Dormly.repository.ProfileRepository;
import com.example.Dormly.repository.ThreadsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ChatService {
    /// we use this service to persist messages and render them to the user if the user received it when
    ///they were not connected to the websocket

    private final ThreadsRepository threadsRepository;
    private final MessageRepository messageRepository;
    private final ListingRepository listingRepository;
    private final PreSignedUrlService preSignedUrlService;
    private final ProfileRepository profileRepository;

    ///whenever a user clicks on 'message seller' ideally what we want is to check if these two users have a conversation
    /// about that specific listing, if so we return the associated message object including the content.
    /// we want to persist the images in order of their created date

    public ThreadsDto getConversationThreadForListing(String buyer, Long listingId) {
        Listing listing  = listingRepository.findById(listingId)
                .orElseThrow(()->new ListingNotFoundException("listing id does not exist"));

        /// get the owner of the listing
        Profile seller = listing.getProfile();

        /// ensure the user is not trying to message himself as the seller
        if(buyer.equals(seller.getUser().getEmail())){
            throw new RuntimeException("users can not message themselves from the listings page");
        }

        ///this should only return a single object, a buyer and seller only have one thread FOR a specific listing
        Optional<Threads> findThreadsBetweenUsers = threadsRepository.findChatsByListingAndUsers(buyer, seller, listingId);

        if(findThreadsBetweenUsers.isEmpty()){
            return new ThreadsDto(); //returns an empty object if no chats
        }


        /// we return a dto of messages, and ensure they are in order of oldest to newest
            ThreadsDto threadsDto = ThreadsDto.convertToDto(findThreadsBetweenUsers.get());


        /// we can set the profile pictures of the usesr for the thread
        /// the buyer is the principal(the authenticated user) and the seller is the listing owner
        threadsDto.getBuyer().setImage(preSignedUrlService.generateProfilePreSignedUrlByEmail(buyer));
        threadsDto.getSeller().setImage(preSignedUrlService.generateProfilePreSignedUrlByEmail(seller.getUser().getEmail()));
        return threadsDto;
    }


    /// retrieve all the profiles users have in their inbox,
    ///for each chat id we check if the user is either a seller or a buyer
    /// this way we know what conversations the users had

    public List<ThreadsDto> FindInboxPreview(String userEmail){
        Profile profile = profileRepository.findByEmail(userEmail)
                .orElseThrow(()->new ProfileNotFoundException("profile does not exist"));

        List<Threads> findUserChats = threadsRepository.findUserInbox(profile);

        if(findUserChats.isEmpty()){
            /// the user may not have any conversations yet
            return Collections.emptyList();
        }

        List<ThreadsDto> inboxPreview = findUserChats.stream()
                .filter(t-> t.getIsDeleted().equals(false))
                .map(ThreadsDto::convertToDto)
                .toList();

        /// we want to only call the s3 presigned service for the users that this authenticated user communicated with
        /// this prevents us from repeated aws api calls
        for(ThreadsDto threads : inboxPreview){
            if(threads.getSeller().getEmail().equals(userEmail)){
                threads.getBuyer()
                        .setImage(preSignedUrlService.generateProfilePreSignedUrlByEmail(threads.getBuyer().getEmail()));
            }
            else if(threads.getBuyer().getEmail().equals(userEmail)){
                threads.getSeller().setImage(preSignedUrlService.generateProfilePreSignedUrlByEmail(threads.getSeller().getEmail()));
            }
            else{
                throw new RuntimeException("user must be either seller, buyer");
            }
        }
        return inboxPreview;
    }







}
