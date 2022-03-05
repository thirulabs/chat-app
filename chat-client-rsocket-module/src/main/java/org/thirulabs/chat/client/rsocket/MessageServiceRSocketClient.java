package org.thirulabs.chat.client.rsocket;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import org.thirulabs.chat.client.service.ClientType;
import org.thirulabs.chat.client.service.MessageServiceClient;
import org.thirulabs.chat.commons.Message;
import org.thirulabs.chat.commons.Status;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * https://docs.spring.io/spring-framework/docs/current/reference/html/rsocket.html
 * https://docs.spring.io/spring-integration/reference/html/rsocket.html
 */
@Service
@Qualifier(ClientType.RSOCKET)
@RequiredArgsConstructor
@ConditionalOnProperty(value = "chat.message.type", havingValue = "json")
public class MessageServiceRSocketClient implements MessageServiceClient {
    private final RSocketRequester rSocketRequester;


    @Override
    public String type() {
        return ClientType.RSOCKET;
    }

    @Override
    public Optional<Message> findById(Long id) {
        Mono<Message> messageMono = rSocketRequester
                .route("find-by-id")
                .data(id)
                .retrieveMono(Message.class);
        return messageMono.blockOptional();
    }

    @Override
    public List<Message> findAll() {
        Mono<Message[]> mono = rSocketRequester
                .route("find-all")
                .retrieveMono(Message[].class);
        return Arrays.asList(mono.block());
    }

    @Override
    public Message add(Message message) {
        Mono<Message> messageMono = rSocketRequester
                .route("add-message")
                .data(message)
                .retrieveMono(Message.class);
        return messageMono.block();
    }

    @Override
    public boolean update(Long id, Message message) {
        message.setId(id);
        Mono<Status> resultMono = rSocketRequester
                .route("update-message")
                .data(message)
                .retrieveMono(Status.class);
        return resultMono.block().isSuccess();
    }

    @Override
    public boolean remove(Long id) {
        Mono<Status> resultMono = rSocketRequester
                .route("remove-by-id")
                .data(id)
                .retrieveMono(Status.class);
        return resultMono.block().isSuccess();
    }

    @Override
    public void removeAll() {
        Mono<Void> resultMono = rSocketRequester
                .route("remove-all")
                .retrieveMono(Void.class);
        resultMono.block();
    }
}
