package org.thirulabs.chat.client.verifier;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.thirulabs.chat.client.service.ClientType;
import org.thirulabs.chat.client.service.MessageServiceClient;
import org.thirulabs.chat.commons.Message;
import org.thirulabs.chat.commons.MessageFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Order(1)
public class ChatClientVerifier implements ApplicationRunner {
    @Autowired
    @Qualifier(ClientType.GRPC)
    private MessageServiceClient messageServiceClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        cleanUp();
        verifyClient(100);
    }

    private void cleanUp() {
        int count = messageServiceClient.count();
        messageServiceClient.removeAll();
        log.info("{} messages cleaned up.", count);
    }

    private void verifyClient(int count){
        log.info("{} client verification started.", messageServiceClient.type());
        final String message1 = "This is a sample message";
        final String message2 = "This is second message used to replace first message";

        List<Long> idList = new ArrayList<>();
        //adding messages
        for(int i=0;i<count;i++) {
            Message message = messageServiceClient.add(MessageFactory.create(message1));
            idList.add(message.getId());
            Assert.isTrue(message1.equals(message.getMessage()), ()-> "Message not equal");
            Assert.isTrue(message.getId() != null, ()-> "ID is null");
        }

        assert count == messageServiceClient.count();

        //updating messages
        for(Long id: idList){
            boolean updated = messageServiceClient.update(id, MessageFactory.create(message2));
            Assert.isTrue(updated, ()-> "Updated flag is false");
        }

        //verifying individual messages
        for(Long id: idList){
            Optional<Message> message = messageServiceClient.findById(id);
            Assert.isTrue(message.isPresent(), ()-> "Find returned empty message");
        }

        //removing individual messages
        for(Long id: idList){
            boolean removed = messageServiceClient.remove(id);
            Assert.isTrue(removed, ()-> "Removed flag is false");
        }

        Assert.isTrue(messageServiceClient.count() == 0, ()-> "Message count is not 0");
        log.info("{} client successfully verified.", messageServiceClient.type());
    }
}
