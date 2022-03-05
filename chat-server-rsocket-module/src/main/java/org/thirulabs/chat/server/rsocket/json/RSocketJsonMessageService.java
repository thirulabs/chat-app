package org.thirulabs.chat.server.rsocket.json;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.thirulabs.chat.commons.Message;
import org.thirulabs.chat.commons.Status;
import org.thirulabs.chat.server.service.MessageService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RSocketJsonMessageService {
    private final MessageService messageService;

    @MessageMapping("find-by-id")
    public Message findById(Long id) {
        return messageService.findById(id);
    }

    @MessageMapping("find-all")
    public List<Message> findAll() {
        return messageService.findAll();
    }

    @MessageMapping("add-message")
    public Message add(Message message) {
        return messageService.add(message);
    }

    @MessageMapping("update-message")
    public Status update(Message message) {
        return new Status(messageService.update(message.getId(), message));
    }

    @MessageMapping("remove-by-id")
    public Status remove(Long id) {
        return new Status(messageService.remove(id));
    }

    @MessageMapping("remove-all")
    public void removeAll() {
        messageService.removeAll();
    }
}
