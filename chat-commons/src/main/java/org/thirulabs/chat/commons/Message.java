package org.thirulabs.chat.commons;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {
    private Long id;
    private String message;
    private LocalDateTime createdTs;
    private int likes;
}
