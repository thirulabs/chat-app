package org.thirulabs.chat.server.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thirulabs.chat.commons.proto.Message;
import org.thirulabs.chat.commons.proto.MessageArray;
import org.thirulabs.chat.commons.proto.MessageID;
import org.thirulabs.chat.commons.proto.Status;
import org.thirulabs.chat.commons.proto.grpc.MessageServiceGrpc;
import org.thirulabs.chat.server.service.proto.ProtoMessageService;

@Service
@RequiredArgsConstructor
public class GrpcMessageService extends MessageServiceGrpc.MessageServiceImplBase {
    private final ProtoMessageService messageService;

    @Override
    public void add(Message request, StreamObserver<Message> responseObserver) {
        var message = messageService.add(request);
        responseObserver.onNext(message);
        responseObserver.onCompleted();
    }

    @Override
    public void update(Message request, StreamObserver<Status> responseObserver) {
        var status = messageService.update(request);
        responseObserver.onNext(status);
        responseObserver.onCompleted();
    }

    @Override
    public void removeById(MessageID request, StreamObserver<Status> responseObserver) {
        var status = messageService.remove(request);
        responseObserver.onNext(status);
        responseObserver.onCompleted();
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
        responseObserver.onNext(list);
        responseObserver.onCompleted();
    }

    @Override
    public void findById(MessageID request, StreamObserver<Message> responseObserver) {
        var message = messageService.findById(request);
        responseObserver.onNext(message);
        responseObserver.onCompleted();
    }
}
