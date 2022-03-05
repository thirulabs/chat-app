package org.thirulabs.chat.client.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
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
@RequiredArgsConstructor
public class ChatClientRunner implements ApplicationRunner {
    private final MessageServiceClient client;
    @Value("${chat.warmup.count:1}")
    private int warmupCount;
    @Value("${chat.operation.count:1}")
    private int operationCount;
    @Value("${chat.iteration.count:1}")
    private int iterationCount;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //testSingleCrudOperation();
        testClientPerformance();
    }

    private void testSingleCrudOperation(){
        Message message = client.add(MessageFactory.create("Sample Create Message"));
        Long id = message.getId();
        log.info("Message Added id: {} message: {}", message.getId(), message);
        boolean updated = client.update(message.getId(), MessageFactory.create("Sample Update Message"));
        log.info("Message Updated {}", updated);
        int count = client.count();
        log.info("Message Count {}", count);
        client.remove(message.getId());
        log.info("Message Removed {}", id);
        count = client.count();
        log.info("Message Count {}", count);
    }

    private void testClientPerformance(){
        log.info("Warming up count {}", warmupCount);
        testServiceClient(0, warmupCount);
        log.info("Running {} iterations", iterationCount);
        for(int i=1;i<=iterationCount;i++){
            testServiceClient(i, operationCount);
        }
    }

    private void testServiceClient(int iterationNumber, int operationCount){
        final String message1 = "This is a sample message";
        final String message2 = "This is second message used to replace first message";

        Instant startTime = Instant.now();
        List<Long> idList = new ArrayList<>();
        //adding messages
        for(int i=0;i<operationCount;i++) {
            Message message = client.add(MessageFactory.create(message1));
            idList.add(message.getId());
        }
        //updating messages
        for(Long id: idList){
            client.update(id, MessageFactory.create(message2));
        }

        //verifying individual messages
        for(Long id: idList){
            Optional<Message> message = client.findById(id);
            if(message.isEmpty()){
                log.warn("Message id {} not found", id);
            }
        }

        //removing individual messages
        for(Long id: idList){
            client.remove(id);
        }

        Instant endTime = Instant.now();
        log.info("Iteration: {} Inserts: {} Updates: {} Finds: {} Deletes: {} ClientType: {} Time taken: {} ms",
                iterationNumber, operationCount, idList.size(), idList.size(), idList.size(), client.type(),
                Duration.between(startTime, endTime).toMillis());
    }
}
