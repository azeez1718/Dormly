package com.example.Dormly.dto;

import com.example.Dormly.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MessageDto {
    private String email;
    private String message;
    private LocalDateTime timestamp;

    public MessageDto(Message message) {
        this.email = message.getSenderId().getUser().getEmail();
        this.message = message.getContent();
        this.timestamp = message.getTimestamp();
    }


}
