package org.thirulabs.chat.commons;

import java.time.LocalDateTime;

public class MessageFactory {
    public static Message create(String text){
        Message message = new Message();
        message.setMessage(text);
        message.setCreatedTs(LocalDateTime.now());
        message.setLikes(0);
        return message;
    }
}
