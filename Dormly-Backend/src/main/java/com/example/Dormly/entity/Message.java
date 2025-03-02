package com.example.Dormly.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
public class Message {
    /**
     * The conversation entity holds unique conversation between two users associated to a specific listing
     * in the messages entity many persisted messages reference a single conversation id
     * for a conversation about a specific listing between two users we can find the conversation,
     * then from the conversation, the message object associated to that conversation is queried
     */


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Profile senderId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @JsonFormat(shape = "YYYY-MM-DD ")
    private LocalDateTime timestamp;

}
