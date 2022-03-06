package org.thirulabs.chat.client.rsocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.codec.protobuf.ProtobufDecoder;
import org.springframework.http.codec.protobuf.ProtobufEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.thirulabs.chat.commons.annotation.JsonEncoding;
import org.thirulabs.chat.commons.annotation.ProtobufEncoding;
import org.thirulabs.chat.commons.annotation.RSocket;

@Configuration
@RSocket
public class RSocketConfig {
    @Value("${chat.server.rsocket.host}")
    private String rsocketHost;

    @Value("${chat.server.rsocket.port}")
    private int rsocketPort;

    @Bean
    @JsonEncoding
    public RSocketStrategies rSocketJsonStrategies() {
        return RSocketStrategies.builder()
                .encoders(encoders ->  encoders.add(new Jackson2JsonEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2JsonDecoder()))
                .build();
    }
    @Bean
    @ProtobufEncoding
    public RSocketStrategies rSocketProtobufStrategies() {
        return RSocketStrategies.builder()
                .encoders(encoders ->  encoders.add(new ProtobufEncoder()))
                .decoders(decoders -> decoders.add(new ProtobufDecoder()))
                .build();
    }

    @Bean
    public RSocketRequester rSocketRequester(RSocketStrategies rSocketStrategies) {
        return RSocketRequester.builder()
                .rsocketStrategies(rSocketStrategies)
                .tcp(rsocketHost, rsocketPort);
    }
}
