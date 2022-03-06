package org.thirulabs.chat.server.service.persistence;


import org.springframework.stereotype.Service;
import org.thirulabs.chat.commons.Message;
import org.thirulabs.chat.server.service.MessageService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryMessageService implements MessageService {
    private Map<Long, Message> messageMap = new ConcurrentHashMap<>();
    private AtomicLong idGenerator = new AtomicLong(0);
    @Override
    public Message findById(Long id) {
        return messageMap.get(id);
    }

    @Override
    public List<Message> findAll() {
        return List.copyOf(messageMap.values());
    }

    @Override
    public Message add(Message message) {
        //generate id before saving it to map
        Long id = idGenerator.incrementAndGet();
        message.setId(id);
        messageMap.put(id, message);
        return message;
    }

    @Override
    public boolean update(Long id, Message message) {
        message.setId(id);
        messageMap.replace(id, message);
        return true;
    }

    @Override
    public boolean remove(Long id) {
        //remove method returns the object removed, returns null when key is not found
        return messageMap.remove(id) != null;
    }

    @Override
    public void removeAll() {
        messageMap.clear();
    }
}
