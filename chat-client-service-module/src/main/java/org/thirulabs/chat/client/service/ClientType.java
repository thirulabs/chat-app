package org.thirulabs.chat.client.service;

public interface ClientType {
    String REST = "REST (json)";
    String REST_PROTOBUF = "REST (protobuf)";
    String GRPC = "Grpc";
    String RSOCKET = "RSocket";
    String RSOCKET_PROTOBUF = "RSocket (protobuf)";
    String DUBBO = "Apache Dubbo";
}
