package org.thirulabs.chat.client.service;

public interface ClientType {
    String REST = "REST (json)";
    String REST_PROTOBUF = "REST (protobuf)";
    String GRPC = "Grpc";
    String RSOCKET = "R Socket";
    String DUBBO = "Apache Dubbo";
}
