package org.thirulabs.chat.server.service.proto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thirulabs.chat.commons.MessageMapper;
import org.thirulabs.chat.commons.proto.Message;
import org.thirulabs.chat.commons.proto.MessageArray;
import org.thirulabs.chat.server.service.MessageService;

@Service
@RequiredArgsConstructor
public class ProtoMessageServiceDecorator implements ProtoMessageService{
    private final MessageService messageService;

    @Override
    public Message findById(Long id) {
        var message = messageService.findById(id);
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
    public boolean update(Message message) {
        var msg = MessageMapper.INSTANCE.map(message);
        return messageService.update(msg.getId(), msg);
    }

    @Override
    public boolean remove(Long id) {
        return messageService.remove(id);
    }

    @Override
    public void removeAll() {
        messageService.removeAll();
    }
}
