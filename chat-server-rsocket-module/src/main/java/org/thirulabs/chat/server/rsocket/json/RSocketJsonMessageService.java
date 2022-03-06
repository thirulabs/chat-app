package org.thirulabs.chat.server.rsocket.json;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.thirulabs.chat.commons.Message;
import org.thirulabs.chat.commons.MessagePaths;
import org.thirulabs.chat.commons.Status;
import org.thirulabs.chat.server.service.MessageService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@MessageMapping(MessagePaths.JSON_PREFIX)
public class RSocketJsonMessageService {
    private final MessageService messageService;

    @MessageMapping(MessagePaths.FIND_MESSAGE_BY_ID)
    public Message findById(Long id) {
        return messageService.findById(id);
    }

    @MessageMapping(MessagePaths.FIND_ALL_MESSAGES)
    public List<Message> findAll() {
        return messageService.findAll();
    }

    @MessageMapping(MessagePaths.ADD_MESSAGE)
    public Message add(Message message) {
        return messageService.add(message);
    }

    @MessageMapping(MessagePaths.UPDATE_MESSAGE)
    public Status update(Message message) {
        return new Status(messageService.update(message.getId(), message));
    }

    @MessageMapping(MessagePaths.REMOVE_MESSAGE_BY_ID)
    public Status remove(Long id) {
        return new Status(messageService.remove(id));
    }

    @MessageMapping(MessagePaths.REMOVE_ALL_MESSAGES)
    public void removeAll() {
        messageService.removeAll();
    }
}
