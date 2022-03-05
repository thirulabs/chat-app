package org.thirulabs.chat.commons;

import java.util.Date;

public class MessageFactory {
    public static Message create(String text){
        Message message = new Message();
        message.setMessage(text);
        message.setCreatedTs(new Date());
        message.setLikes(0);
        return message;
    }
}
