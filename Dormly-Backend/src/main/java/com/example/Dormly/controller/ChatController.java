package com.example.Dormly.controller;

import ch.qos.logback.core.joran.conditional.ThenAction;
import com.example.Dormly.dto.ThreadsDto;
import com.example.Dormly.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/Dormly/chats")
public class ChatController {

    private final ChatService chatService;


    /**
     * We return the thread between two users
     * @param ThreadId - used to find the thread that will include the return of the messages between users
     * @return ThreadDTO- used to hide internals of our thread object
     */

    @GetMapping(value = "history/{id}")
    public ResponseEntity<ThreadsDto> chatHistory(@PathVariable("id") Long ThreadId){

        ThreadsDto chatHistory = chatService.getConversationThread(ThreadId);
        return new ResponseEntity<>(chatHistory, HttpStatus.OK);
    }

    /**
     * This returns the inbox of the user, the profiles in which he has messaged
     * @param userdetails - the authenticated user, we use this to find the inbox of the user
     * @return ThreadsDto - we return a collection of threads as the user is involved in many conversations
     */
    @GetMapping(path = "/inbox/preview")
    public ResponseEntity<List<ThreadsDto>> chatPreview(@AuthenticationPrincipal UserDetails userdetails){
        log.info(userdetails.getUsername());
        List<ThreadsDto> chatHistory = chatService.FindInboxPreview(userdetails.getUsername());
        return new ResponseEntity<>(chatHistory, HttpStatus.OK);
    }

    /**
     * find if a Thread exists between a user and a seller, & return the id of that thread
     * @param userDetails - the authenticated user who aims to message a seller
     * @param listingId - the identifier that allows us to find the seller
     * @return id - used for fetching the thread by id later on
     */
    @GetMapping(path= "thread/listing/{id}")
    public ResponseEntity<Long> findIfThreadExists(@AuthenticationPrincipal UserDetails userDetails,
                                                   @PathVariable("id") Long listingId){
        Long ThreadId = chatService.checkThreadExists(userDetails.getUsername(), listingId);
        return new ResponseEntity<>(ThreadId, HttpStatus.OK);
    }



}
