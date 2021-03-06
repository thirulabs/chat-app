package org.thirulabs.chat.client.rsocket.json;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import org.thirulabs.chat.client.service.ClientType;
import org.thirulabs.chat.client.service.MessageServiceClient;
import org.thirulabs.chat.commons.Message;
import org.thirulabs.chat.commons.MessagePaths;
import org.thirulabs.chat.commons.Status;
import org.thirulabs.chat.client.annotation.JsonEncoding;
import org.thirulabs.chat.client.annotation.RSocket;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * https://docs.spring.io/spring-framework/docs/current/reference/html/rsocket.html
 * https://docs.spring.io/spring-integration/reference/html/rsocket.html
 */
@Service
@RSocket
@JsonEncoding
@RequiredArgsConstructor
public class MessageServiceRSocketClient implements MessageServiceClient {
    private final RSocketRequester rSocketRequester;

    @Override
    public ClientType type() {
        return ClientType.RSOCKET;
    }

    private static String jsonPrefix(String path){
        return MessagePaths.JSON_PREFIX + path;
    }

    @Override
    public Optional<Message> findById(Long id) {
        Mono<Message> messageMono = rSocketRequester
                .route(jsonPrefix(MessagePaths.FIND_MESSAGE_BY_ID))
                .data(id)
                .retrieveMono(Message.class);
        return messageMono.blockOptional();
    }

    @Override
    public List<Message> findAll() {
        Mono<Message[]> mono = rSocketRequester
                .route(jsonPrefix(MessagePaths.FIND_ALL_MESSAGES))
                .retrieveMono(Message[].class);
        return Arrays.asList(mono.block());
    }

    @Override
    public Message add(Message message) {
        Mono<Message> messageMono = rSocketRequester
                .route(jsonPrefix(MessagePaths.ADD_MESSAGE))
                .data(message)
                .retrieveMono(Message.class);
        return messageMono.block();
    }

    @Override
    public boolean update(Long id, Message message) {
        message.setId(id);
        Mono<Status> resultMono = rSocketRequester
                .route(jsonPrefix(MessagePaths.UPDATE_MESSAGE))
                .data(message)
                .retrieveMono(Status.class);
        return resultMono.block().isSuccess();
    }

    @Override
    public boolean remove(Long id) {
        Mono<Status> resultMono = rSocketRequester
                .route(jsonPrefix(MessagePaths.REMOVE_MESSAGE_BY_ID))
                .data(id)
                .retrieveMono(Status.class);
        return resultMono.block().isSuccess();
    }

    @Override
    public void removeAll() {
        Mono<Void> resultMono = rSocketRequester
                .route(jsonPrefix(MessagePaths.REMOVE_ALL_MESSAGES))
                .retrieveMono(Void.class);
        resultMono.block();
    }
}
