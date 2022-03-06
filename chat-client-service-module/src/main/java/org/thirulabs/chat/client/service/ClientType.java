package org.thirulabs.chat.client.service;

public enum ClientType {
    REST("REST (json)"),
    REST_PROTOBUF("REST (protobuf)"),
    GRPC("Grpc"),
    RSOCKET("RSocket"),
    RSOCKET_PROTOBUF("RSocket (protobuf)"),
    DUBBO("Apache Dubbo");

    private String type;
    ClientType(String type){
        this.type = type;
    }
    public String getType() {
        return type;
    }
}
