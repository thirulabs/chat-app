package org.thirulabs.chat.client.rsocket.protobuf;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import org.thirulabs.chat.client.service.ClientType;
import org.thirulabs.chat.client.service.MessageServiceClient;
import org.thirulabs.chat.commons.Message;
import org.thirulabs.chat.commons.MessageMapper;
import org.thirulabs.chat.client.annotation.ProtobufEncoding;
import org.thirulabs.chat.client.annotation.RSocket;
import org.thirulabs.chat.commons.MessagePaths;
import org.thirulabs.chat.commons.proto.MessageArray;
import org.thirulabs.chat.commons.proto.MessageID;
import org.thirulabs.chat.commons.proto.Status;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

/**
 * https://docs.spring.io/spring-framework/docs/current/reference/html/rsocket.html
 * https://docs.spring.io/spring-integration/reference/html/rsocket.html
 */
@Service
@RSocket
@ProtobufEncoding
@RequiredArgsConstructor
public class MessageServiceRSocketClient implements MessageServiceClient {
    private final RSocketRequester rSocketRequester;

    @Override
    public String type() {
        return ClientType.RSOCKET_PROTOBUF;
    }

    private static String protoPrefix(String path){
        return MessagePaths.PROTO_PREFIX + path;
    }

    @Override
    public Optional<Message> findById(Long id) {
        var mono = rSocketRequester
                .route(protoPrefix(MessagePaths.FIND_MESSAGE_BY_ID))
                .data(MessageID.newBuilder().setMessageID(id).build())
                .retrieveMono(org.thirulabs.chat.commons.proto.Message.class);
        var optionalMessage = mono.blockOptional();
        if(optionalMessage.isPresent()){
            return Optional.of(MessageMapper.INSTANCE.map(optionalMessage.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<Message> findAll() {
        var mono = rSocketRequester
                .route(protoPrefix(MessagePaths.FIND_ALL_MESSAGES))
                .retrieveMono(MessageArray.class);
        return MessageMapper.INSTANCE.asList(mono.block().getArrayList());
    }

    @Override
    public Message add(Message message) {
        var mono = rSocketRequester
                .route(protoPrefix(MessagePaths.ADD_MESSAGE))
                .data(MessageMapper.INSTANCE.map(message))
                .retrieveMono(org.thirulabs.chat.commons.proto.Message.class);
        return MessageMapper.INSTANCE.map(mono.block());
    }

    @Override
    public boolean update(Long id, Message message) {
        message.setId(id);
        var resultMono = rSocketRequester
                .route(protoPrefix(MessagePaths.UPDATE_MESSAGE))
                .data(MessageMapper.INSTANCE.map(message))
                .retrieveMono(Status.class);
        return resultMono.block().getSuccess();
    }

    @Override
    public boolean remove(Long id) {
        Mono<Status> resultMono = rSocketRequester
                .route(protoPrefix(MessagePaths.REMOVE_MESSAGE_BY_ID))
                .data(MessageID.newBuilder().setMessageID(id).build())
                .retrieveMono(Status.class);
        return resultMono.block().getSuccess();
    }

    @Override
    public void removeAll() {
        Mono<Void> resultMono = rSocketRequester
                .route(protoPrefix(MessagePaths.REMOVE_ALL_MESSAGES))
                .retrieveMono(Void.class);
        resultMono.block();
    }
}
