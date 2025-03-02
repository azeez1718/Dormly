package com.example.Dormly.controller;

import com.example.Dormly.dto.ThreadsDto;
import com.example.Dormly.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/Dormly/chats")
public class ChatController {

    private final ChatService chatService;


    /// associates with the profile Id,
    ///we check if a seller and a user already have a chat history, otherwise by defauult a new one is created
    /// we use the listing Id to find who the seller is.

    @GetMapping(value = "history/{id}")
    public ResponseEntity<ThreadsDto> chatHistory(@AuthenticationPrincipal UserDetails userdetails,
                                                  @PathVariable("id") Long listingId){

        ThreadsDto chatHistory = chatService.UserConversationThread(userdetails.getUsername(), listingId);
        return new ResponseEntity<>(chatHistory, HttpStatus.OK);
    }


    @GetMapping(path = "/inbox/preview")
    public ResponseEntity<List<ThreadsDto>> chatPreview(@AuthenticationPrincipal UserDetails userdetails){
        List<ThreadsDto> chatHistory = chatService.FindInboxPreview(userdetails.getUsername());
        return new ResponseEntity<>(chatHistory, HttpStatus.OK);
    }


}
