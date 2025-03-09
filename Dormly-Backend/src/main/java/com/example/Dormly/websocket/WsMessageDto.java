package com.example.Dormly.websocket;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WsMessageDto {
    private String content;
    private String recipient;
    private Long threadId;
}
