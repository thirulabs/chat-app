package org.thirulabs.chat.server.rsocket.protobuf;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.thirulabs.chat.commons.MessagePaths;
import org.thirulabs.chat.commons.proto.Message;
import org.thirulabs.chat.commons.proto.MessageArray;
import org.thirulabs.chat.commons.proto.MessageID;
import org.thirulabs.chat.commons.proto.Status;
import org.thirulabs.chat.server.service.proto.ProtoMessageService;

@Controller
@RequiredArgsConstructor
@MessageMapping(MessagePaths.PROTO_PREFIX)
public class RSocketProtobufMessageService {
    private final ProtoMessageService protoMessageService;

    @MessageMapping(MessagePaths.FIND_MESSAGE_BY_ID)
    public Message findById(MessageID id) {
        return protoMessageService.findById(id);
    }

    @MessageMapping(MessagePaths.FIND_ALL_MESSAGES)
    public MessageArray findAll() {
        return protoMessageService.findAll();
    }

    @MessageMapping(MessagePaths.ADD_MESSAGE)
    public Message add(Message message) {
        return protoMessageService.add(message);
    }

    @MessageMapping(MessagePaths.UPDATE_MESSAGE)
    public Status update(Message message) {
        return protoMessageService.update(message);
    }

    @MessageMapping(MessagePaths.REMOVE_MESSAGE_BY_ID)
    public Status remove(MessageID id) {
        return protoMessageService.remove(id);
    }

    @MessageMapping(MessagePaths.REMOVE_ALL_MESSAGES)
    public void removeAll() {
        protoMessageService.removeAll();
    }
}
