package com.example.Dormly.websocket;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String content;
    private String recipient;
}
