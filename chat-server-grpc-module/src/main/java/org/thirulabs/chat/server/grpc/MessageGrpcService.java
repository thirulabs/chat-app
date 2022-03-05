package org.thirulabs.chat.server.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thirulabs.chat.commons.MessageMapper;
import org.thirulabs.chat.commons.proto.Message;
import org.thirulabs.chat.commons.proto.MessageArray;
import org.thirulabs.chat.commons.proto.MessageID;
import org.thirulabs.chat.commons.proto.grpc.MessageServiceGrpc;
import org.thirulabs.chat.server.service.MessageService;

@Service
@RequiredArgsConstructor
public class MessageGrpcService extends MessageServiceGrpc.MessageServiceImplBase {
    private final MessageService messageService;

    @Override
    public void add(Message request, StreamObserver<Message> responseObserver) {
        var message = messageService.add(MessageMapper.INSTANCE.map(request));
        responseObserver.onNext(MessageMapper.INSTANCE.map(message));
        responseObserver.onCompleted();
    }

    @Override
    public void update(Message request, StreamObserver<Message> responseObserver) {
        boolean updated = messageService.update(request.getMessageID(), MessageMapper.INSTANCE.map(request));
        //TODO change the return type of rpc to boolean
        responseObserver.onNext(request); //TODO change it to boolean instead of returning the request object
        responseObserver.onCompleted();
    }

    @Override
    public void removeById(MessageID request, StreamObserver<Empty> responseObserver) {
        messageService.remove(request.getMessageID());
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
        //TODO handle error cases
    }

    @Override
    public void removeAll(Empty request, StreamObserver<Empty> responseObserver) {
        messageService.removeAll();
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void findAll(Empty request, StreamObserver<MessageArray> responseObserver) {
        var list = messageService.findAll();
        responseObserver.onNext(MessageArray.newBuilder().addAllArray(MessageMapper.INSTANCE.map(list)).build());
        responseObserver.onCompleted();
    }

    @Override
    public void findById(MessageID request, StreamObserver<Message> responseObserver) {
        var message = messageService.findById(request.getMessageID());
        responseObserver.onNext(MessageMapper.INSTANCE.map(message));
        responseObserver.onCompleted();
    }
}