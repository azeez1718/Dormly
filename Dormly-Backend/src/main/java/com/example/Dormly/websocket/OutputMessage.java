package com.example.Dormly.websocket;

import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Getter
@Setter
public class OutputMessage {

    private String message;
    private String sender;
    private LocalDateTime timestamp;
}
