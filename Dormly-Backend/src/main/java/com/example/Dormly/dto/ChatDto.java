package com.example.Dormly.dto;


import com.example.Dormly.entity.Chat;
import com.example.Dormly.entity.Profile;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatDto {
    private ProfileDto seller;

    private ProfileDto buyer;

    private String message;


    /// this is the information of the listing
    private String title;
    private String description;


    /// this needs to be in order of time not date
    private LocalDateTime createdAt;

    /// on the instance of chatDtos for each profileDTO(seller and buyer) we can set the value of profile URL by passing in the
    public static ChatDto convertToDto(Chat chat) {
        return ChatDto.builder()
                .buyer(ProfileDto.from(chat.getBuyer()))
                .seller(ProfileDto.from(chat.getSeller()))
                .message(chat.getContent())
                .title(chat.getListing().getTitle())
                .description(chat.getListing().getDescription())
                .createdAt(chat.getCreatedAt())
                .build();
    }



}
