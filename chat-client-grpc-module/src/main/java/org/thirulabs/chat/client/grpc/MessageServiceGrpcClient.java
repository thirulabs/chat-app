package org.thirulabs.chat.client.grpc;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thirulabs.chat.client.service.ClientType;
import org.thirulabs.chat.client.service.MessageServiceClient;
import org.thirulabs.chat.commons.Message;
import org.thirulabs.chat.commons.MessageMapper;
import org.thirulabs.chat.commons.annotation.Grpc;
import org.thirulabs.chat.commons.proto.MessageID;
import org.thirulabs.chat.commons.proto.grpc.MessageServiceGrpc;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
@Grpc
public class MessageServiceGrpcClient implements MessageServiceClient {
    @Value("${chat.server.grpc.host}")
    private String grpcHost;
    @Value("${chat.server.grpc.port}")
    private int grpcPort;

    private ManagedChannel channel;

    @PostConstruct
    void init(){
        channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort).usePlaintext().build();
    }

    @Override
    public String type() {
        return ClientType.GRPC;
    }

    @Override
    public Optional<Message> findById(Long id) {
        MessageServiceGrpc.MessageServiceBlockingStub stub = MessageServiceGrpc.newBlockingStub(channel);
        var pMessage = stub.findById(MessageID.newBuilder().setMessageID(id).build());
        return Optional.ofNullable(MessageMapper.INSTANCE.map(pMessage));
    }

    @Override
    public List<Message> findAll() {
        MessageServiceGrpc.MessageServiceBlockingStub stub = MessageServiceGrpc.newBlockingStub(channel);
        var pList = stub.findAll(Empty.newBuilder().build());
        return MessageMapper.INSTANCE.asList(pList.getArrayList());
    }

    @Override
    public Message add(Message message) {
        MessageServiceGrpc.MessageServiceBlockingStub stub = MessageServiceGrpc.newBlockingStub(channel);
        var pMsg = stub.add(MessageMapper.INSTANCE.map(message));
        return MessageMapper.INSTANCE.map(pMsg);
    }

    @Override
    public boolean update(Long id, Message message) {
        MessageServiceGrpc.MessageServiceBlockingStub stub = MessageServiceGrpc.newBlockingStub(channel);
        message.setId(id);
        stub.update(MessageMapper.INSTANCE.map(message));
        return true;
    }

    @Override
    public boolean remove(Long id) {
        MessageServiceGrpc.MessageServiceBlockingStub stub = MessageServiceGrpc.newBlockingStub(channel);
        stub.removeById(MessageID.newBuilder().setMessageID(id).build());
        return true;
    }

    @Override
    public void removeAll() {
        MessageServiceGrpc.MessageServiceBlockingStub stub = MessageServiceGrpc.newBlockingStub(channel);
        stub.removeAll(Empty.newBuilder().build());
    }
}
