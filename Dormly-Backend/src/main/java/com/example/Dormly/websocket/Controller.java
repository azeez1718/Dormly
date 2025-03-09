package com.example.Dormly.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.security.Principal;
import java.time.LocalDateTime;

@org.springframework.stereotype.Controller
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final SimpMessagingTemplate simpMessagingTemplate;
    ///
    /// The @MessageMapping is used to route all /app/placeholder destinations to their specific methods
    /// we use the principal to define the user who is the sender, and via a UI action
    /// we also include recipient to define who the end user is
    /// we create an output message object which contains a sender and the content
    /// we then send this output message to the user
    /// The message object just has the recipient(to whom we send to) and the content, the sender is fetched from the principal
    @MessageMapping("/chat/send")
    public void sendMessage(@Payload Message message , @AuthenticationPrincipal Principal principal){
        log.info(message.toString());
        log.info(principal.toString());
        OutputMessage outputMessage = new OutputMessage(
                message.getContent(),
                principal.getName(),
                LocalDateTime.now()
        );
        log.info(outputMessage.toString());
        /// the server will send back something like '/user/james/queue/chat'
        simpMessagingTemplate.convertAndSendToUser(message.getRecipient(),"/queue/chat", outputMessage);


    }
}
