package com.example.Dormly.websocket;

import jakarta.annotation.Nullable;
import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Getter
@Setter
public class OutputMessage {

    private String message;
    private String sender;
}
