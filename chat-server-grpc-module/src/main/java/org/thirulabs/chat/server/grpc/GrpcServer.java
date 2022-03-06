package org.thirulabs.chat.server.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GrpcServer implements ApplicationRunner {
    private final GrpcMessageService grpcMessageService;
    @Value("${grpc.server.port}")
    private int grpcPort;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Server server = ServerBuilder
                .forPort(grpcPort)
                .addService(grpcMessageService)
                .build();
        server.start();
        log.info("GRPC server started on port {}.", grpcPort);
    }
}
