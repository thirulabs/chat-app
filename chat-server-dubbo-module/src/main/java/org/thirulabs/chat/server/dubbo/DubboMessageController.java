package org.thirulabs.chat.server.dubbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.thirulabs.chat.commons.dubbo.DubboMessage;
import org.thirulabs.chat.commons.dubbo.DubboMessageMapper;
import org.thirulabs.chat.commons.dubbo.DubboMessageService;
import org.thirulabs.chat.server.service.MessageService;

import java.util.List;

public class DubboMessageController implements DubboMessageService {
    @Autowired
    private MessageService messageService;

    @Override
    public DubboMessage findById(Long id) {
        var message = messageService.findById(id);
        return DubboMessageMapper.INSTANCE.map(message);
    }

    @Override
    public List<DubboMessage> findAll() {
        var messageList = messageService.findAll();
        return DubboMessageMapper.INSTANCE.map(messageList);
    }

    @Override
    public DubboMessage add(DubboMessage dubboMessage) {
        var message = DubboMessageMapper.INSTANCE.map(dubboMessage);
        var savedMessage = messageService.add(message);
        return DubboMessageMapper.INSTANCE.map(savedMessage);
    }

    @Override
    public boolean update(Long id, DubboMessage dubboMessage) {
        var message = DubboMessageMapper.INSTANCE.map(dubboMessage);
        return messageService.update(id, message);
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
