package org.thirulabs.chat.commons;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Long id;
    private String message;
    private Date createdTs;
    private int likes;
}
