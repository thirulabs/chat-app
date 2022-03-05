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
        log.info("Warming up count {}", warmupCount);
        testServiceClient(0, warmupCount);
        log.info("Running {} iterations", iterationCount);
        for(int i=1;i<=iterationCount;i++){
            testServiceClient(i, operationCount);
        }
    }

    private void testServiceClient(int iterationNumber, int operationCount){
        Instant startTime = Instant.now();
        Message message = client.add(MessageFactory.create("Hello Server"));
        log.info("message added {}", message);
        boolean updated = client.update(message.getId(), MessageFactory.create("Hello Client"));
        log.info("Updated {}", updated);
        int count = client.count();
        log.info("Message count {}", count);
        client.remove(message.getId());
        log.info("Removed");
        count = client.count();
        log.info("Messages after removal {}", count);

        Instant endTime = Instant.now();
        log.info("Iteration: {} Inserts: {} Updates: {} ClientType: {} Time taken: {} ms",
                iterationNumber, operationCount, operationCount, client.type(), Duration.between(startTime, endTime).toMillis());
    }
}
