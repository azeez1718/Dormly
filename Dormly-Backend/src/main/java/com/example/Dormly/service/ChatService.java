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
    /// this is done using the checkThreadExists function, the client side uses that thread to render the thread
    /// we want to persist the images in order of their created date

    public ThreadsDto getConversationThread(String userEmail, Long ThreadId) {
        Threads getThread = threadsRepository.findById(ThreadId)
                .orElseThrow(()-> new ThreadNotFoundException("Thread not found"));

        ///we return a dto of messages, and ensure they are in order of oldest to newest
        ThreadsDto threadsDto = ThreadsDto.convertToDto(getThread);


        /// we can set the profile pictures of the user for the thread
        /// the buyer is the principal(the authenticated user) and the seller is the listing owner
        threadsDto.getBuyer().setImage(preSignedUrlService.
                generateProfilePreSignedUrlByEmail(threadsDto.getBuyer().getEmail()));
        threadsDto.getSeller().setImage(preSignedUrlService.
                generateProfilePreSignedUrlByEmail(threadsDto.getSeller().getEmail()));

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
        for(ThreadsDto threads : inboxPreview) {
            /// we set the messages to null as we dont want to return it, removed during serialization
            threads.setMessages(null);
            if (threads.getSeller().getEmail().equals(userEmail)) {
                threads.getBuyer()
                        .setImage(preSignedUrlService.generateProfilePreSignedUrlByEmail(threads.getBuyer().getEmail()));
            } else if (threads.getBuyer().getEmail().equals(userEmail)) {
                threads.getSeller().setImage(preSignedUrlService.generateProfilePreSignedUrlByEmail(threads.getSeller().getEmail()));
            } else {
                throw new RuntimeException("user must be either seller, buyer");
            }
        }
        return inboxPreview;
    }


    /// based on the listing id we check if two users have a conversation for a specific listing
    /// if they do we return their existing thread id, if not we create a thread for the users and return a new thread id
    /// the userEmail is the authenticated user or buyer in this sense
    public Long checkThreadExists(String userEmail, Long listingId) {
        Listing findListing = listingRepository.findById(listingId)
                .orElseThrow(()->new ListingNotFoundException("listing id does not exist"));

        Profile seller = findListing.getProfile();

        /// ensure the user is not trying to message himself as the seller
        if(userEmail.equals(seller.getUser().getEmail())){
            throw new RuntimeException("users can not message themselves from the listings page");
        }

        ///this should only return a single object, a buyer and seller only have one thread FOR a specific listing
        Optional<Threads> findThreadBetweenUsers = threadsRepository.
                findChatsByListingAndUsers(userEmail, seller, listingId);

        /// if the thread doesnt exist, we creae one and return the newly created threadId
        if(findThreadBetweenUsers.isEmpty()){
            Threads saveThread = Threads.builder()
                    .buyer(profileRepository.findByEmail(userEmail).orElseThrow(()->new ProfileNotFoundException("A profile does not exist for this user")))
                    .seller(seller)
                    .listing(findListing)
                    .isDeleted(Boolean.FALSE)
                    .build();
            threadsRepository.save(saveThread);
            return saveThread.getId();
        }
        Threads existingThread = findThreadBetweenUsers.get();
        return existingThread.getId();

    }






}
