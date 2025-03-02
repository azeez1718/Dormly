package com.example.Dormly.dto;



import com.example.Dormly.entity.Message;
import com.example.Dormly.entity.Threads;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ThreadsDto {
    private Long id;
    private ProfileDto seller;
    private ProfileDto buyer;
    /// this is the information of the listing
    private String title;
    private String description;
    private List<MessageDto> messages;
    private Long listingId;


    /// on the instance of Threads dto for each profileDTO(seller and buyer) we can set the value of profile URL
    /// we convert messages to a dto, to prevent serializing the sender entity as it contains sensitive information
    public static ThreadsDto convertToDto(Threads threads) {
        return ThreadsDto.builder()
                .id(threads.getId())
                .buyer(ProfileDto.from(threads.getBuyer()))
                .seller(ProfileDto.from(threads.getSeller()))
                .messages(threads.getMessages().stream().map(MessageDto::new).collect(Collectors.toList()))
                .title(threads.getListing().getTitle())
                .listingId(threads.getListing().getId())
                .build();
    }







}
