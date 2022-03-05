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
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

@Configuration
public class RSocketConfig {
    @Value("${chat.server.rsocket.host}")
    private String rsocketHost;

    @Value("${chat.server.rsocket.port}")
    private int rsocketPort;

    @Value("${chat.message.type}")
    private String messageType;

    @Bean
    public RSocketRequester rSocketRequester() {
        boolean protobuf = "protobuf".equals(messageType);
        RSocketStrategies strategies = RSocketStrategies.builder()
                .encoders(encoders -> {
                    if (protobuf) {
                        encoders.add(new ProtobufEncoder());
                    } else {
                        encoders.add(new Jackson2JsonEncoder());
                    }
                })
                .decoders(decoders -> {
                    if (protobuf) {
                        decoders.add(new ProtobufDecoder());
                    } else {
                        decoders.add(new Jackson2JsonDecoder());
                    }
                })
                .build();

        RSocketRequester.Builder builder = RSocketRequester.builder().rsocketStrategies(strategies);
        if (protobuf) {
            builder.dataMimeType(new MimeType("application", "x-protobuf"));
        } else {
            builder.dataMimeType(MimeTypeUtils.APPLICATION_JSON);
        }
        return builder.tcp(rsocketHost, rsocketPort);
    }
}
