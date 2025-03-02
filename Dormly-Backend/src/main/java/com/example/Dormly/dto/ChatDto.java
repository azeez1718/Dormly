package com.example.Dormly.dto;


import com.example.Dormly.entity.Chat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatDto {
    private UUID id;
    private ProfileDto seller;

    private ProfileDto buyer;

    private List<Conversation> conversation;


    /// this is the information of the listing
    private String title;
    private String description;

    private Long listingId;
    /// this needs to be in order of time not date
    private LocalDateTime createdAt;

    /// on the instance of chatDto's for each profileDTO(seller and buyer) we can set the value of profile URL by passing in the
    public static ChatDto convertToDto(Chat chat) {
        return ChatDto.builder()
                .id(chat.getId())
                .buyer(ProfileDto.from(chat.getBuyer()))
                .seller(ProfileDto.from(chat.getSeller()))
                .conversation(chat.getConversation())
                .title(chat.getListing().getTitle())
                .description(chat.getListing().getDescription())
                .createdAt(chat.getCreatedAt())
                .listingId(chat.getListing().getId())
                .build();
    }



}
