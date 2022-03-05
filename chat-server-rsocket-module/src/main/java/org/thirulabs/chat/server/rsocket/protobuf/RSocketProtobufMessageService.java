package org.thirulabs.chat.server.rsocket.protobuf;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.thirulabs.chat.commons.proto.Message;
import org.thirulabs.chat.commons.proto.MessageArray;
import org.thirulabs.chat.server.service.proto.ProtoMessageService;

//@Controller
@RequiredArgsConstructor
public class RSocketProtobufMessageService {
    private final ProtoMessageService protoMessageService;

    @MessageMapping("find-by-id")
    public Message findById(Long id) {
        return protoMessageService.findById(id);
    }

    @MessageMapping("find-all")
    public MessageArray findAll() {
        return protoMessageService.findAll();
    }

    @MessageMapping("add-message")
    public Message add(Message message) {
        return protoMessageService.add(message);
    }

    @MessageMapping("update-message")
    public boolean update(Message message) {
        return protoMessageService.update(message);
    }

    @MessageMapping("remove-by-id")
    public boolean remove(Long id) {
        return protoMessageService.remove(id);
    }

    @MessageMapping("remove-all")
    public void removeAll() {
        protoMessageService.removeAll();
    }
}
