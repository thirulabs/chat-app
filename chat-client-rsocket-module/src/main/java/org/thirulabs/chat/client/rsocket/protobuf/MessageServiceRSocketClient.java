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

    @Override
    public Optional<Message> findById(Long id) {
        var mono = rSocketRequester
                .route("proto.message.find.by.id")
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
                .route("proto.message.find.all")
                .retrieveMono(MessageArray.class);
        return MessageMapper.INSTANCE.asList(mono.block().getArrayList());
    }

    @Override
    public Message add(Message message) {
        var mono = rSocketRequester
                .route("proto.message.add")
                .data(MessageMapper.INSTANCE.map(message))
                .retrieveMono(org.thirulabs.chat.commons.proto.Message.class);
        return MessageMapper.INSTANCE.map(mono.block());
    }

    @Override
    public boolean update(Long id, Message message) {
        message.setId(id);
        var resultMono = rSocketRequester
                .route("proto.message.update")
                .data(MessageMapper.INSTANCE.map(message))
                .retrieveMono(Status.class);
        return resultMono.block().getSuccess();
    }

    @Override
    public boolean remove(Long id) {
        Mono<Status> resultMono = rSocketRequester
                .route("proto.message.remove.by.id")
                .data(MessageID.newBuilder().setMessageID(id).build())
                .retrieveMono(Status.class);
        return resultMono.block().getSuccess();
    }

    @Override
    public void removeAll() {
        Mono<Void> resultMono = rSocketRequester
                .route("proto.message.remove.all")
                .retrieveMono(Void.class);
        resultMono.block();
    }
}
