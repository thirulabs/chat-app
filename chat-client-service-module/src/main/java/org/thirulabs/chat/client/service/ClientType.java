package org.thirulabs.chat.client.service;

public enum ClientType {
    REST("RESTv1 (json)"),
    REST_V2("RESTv2 (json)"),
    REST_H2("REST H2 (json)"),

    REST_PROTOBUF("RESTv1 (protobuf)"),
    REST_V2_PROTOBUF("RESTv2 (protobuf)"),
    REST_H2_PROTOBUF("REST H2 (protobuf)"),

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
