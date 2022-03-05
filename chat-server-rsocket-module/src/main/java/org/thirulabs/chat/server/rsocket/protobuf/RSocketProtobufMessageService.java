package org.thirulabs.chat.server.rsocket.protobuf;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.thirulabs.chat.commons.proto.Message;
import org.thirulabs.chat.commons.proto.MessageArray;
import org.thirulabs.chat.commons.proto.MessageID;
import org.thirulabs.chat.commons.proto.Status;
import org.thirulabs.chat.server.service.proto.ProtoMessageService;

@Controller
@RequiredArgsConstructor
public class RSocketProtobufMessageService {
    private final ProtoMessageService protoMessageService;

    @MessageMapping("proto.message.find.by.id")
    public Message findById(MessageID id) {
        return protoMessageService.findById(id);
    }

    @MessageMapping("proto.message.find.all")
    public MessageArray findAll() {
        return protoMessageService.findAll();
    }

    @MessageMapping("proto.message.add")
    public Message add(Message message) {
        return protoMessageService.add(message);
    }

    @MessageMapping("proto.message.update")
    public Status update(Message message) {
        return protoMessageService.update(message);
    }

    @MessageMapping("proto.message.remove.by.id")
    public Status remove(MessageID id) {
        return protoMessageService.remove(id);
    }

    @MessageMapping("proto.message.remove.all")
    public void removeAll() {
        protoMessageService.removeAll();
    }
}
