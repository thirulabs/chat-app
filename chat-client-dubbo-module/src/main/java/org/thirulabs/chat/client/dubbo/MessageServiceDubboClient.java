package org.thirulabs.chat.client.dubbo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thirulabs.chat.client.service.ClientType;
import org.thirulabs.chat.client.service.MessageServiceClient;
import org.thirulabs.chat.commons.Message;
import org.thirulabs.chat.commons.dubbo.DubboMessageMapper;
import org.thirulabs.chat.commons.dubbo.DubboMessageService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceDubboClient implements MessageServiceClient {
    private final DubboMessageService dubboMessageService;

    @Override
    public ClientType type() {
        return ClientType.DUBBO;
    }

    @Override
    public Optional<Message> findById(Long id) {
        var message = dubboMessageService.findById(id);
        return Optional.of(DubboMessageMapper.INSTANCE.map(message));
    }

    @Override
    public List<Message> findAll() {
        var messageList = dubboMessageService.findAll();
        return DubboMessageMapper.INSTANCE.asList(messageList);
    }

    @Override
    public Message add(Message message) {
        var dubboMessage = dubboMessageService.add(DubboMessageMapper.INSTANCE.map(message));
        return DubboMessageMapper.INSTANCE.map(dubboMessage);
    }

    @Override
    public boolean update(Long id, Message message) {
        return dubboMessageService.update(id, DubboMessageMapper.INSTANCE.map(message));
    }

    @Override
    public boolean remove(Long id) {
        return dubboMessageService.remove(id);
    }

    @Override
    public void removeAll() {
        dubboMessageService.removeAll();
    }
}
