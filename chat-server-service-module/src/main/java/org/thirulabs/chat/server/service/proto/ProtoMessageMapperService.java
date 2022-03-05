package org.thirulabs.chat.server.service.proto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thirulabs.chat.commons.MessageMapper;
import org.thirulabs.chat.commons.proto.Message;
import org.thirulabs.chat.commons.proto.MessageArray;
import org.thirulabs.chat.commons.proto.MessageID;
import org.thirulabs.chat.commons.proto.Status;
import org.thirulabs.chat.server.service.MessageService;

@Service
@RequiredArgsConstructor
public class ProtoMessageMapperService implements ProtoMessageService{
    private final MessageService messageService;

    @Override
    public Message findById(MessageID id) {
        var message = messageService.findById(id.getMessageID());
        return MessageMapper.INSTANCE.map(message);
    }

    @Override
    public MessageArray findAll() {
        var messageList = messageService.findAll();
        var protoMessageList = MessageMapper.INSTANCE.map(messageList);
        return MessageArray.newBuilder().addAllArray(protoMessageList).build();
    }

    @Override
    public Message add(Message message) {
        var msg = MessageMapper.INSTANCE.map(message);
        msg = messageService.add(msg);
        return MessageMapper.INSTANCE.map(msg);
    }

    @Override
    public Status update(Message message) {
        var msg = MessageMapper.INSTANCE.map(message);
        boolean updated = messageService.update(msg.getId(), msg);
        return Status.newBuilder().setSuccess(updated).build();
    }

    @Override
    public Status remove(MessageID id) {
        boolean removed = messageService.remove(id.getMessageID());
        return Status.newBuilder().setSuccess(removed).build();
    }

    @Override
    public void removeAll() {
        messageService.removeAll();
    }
}
