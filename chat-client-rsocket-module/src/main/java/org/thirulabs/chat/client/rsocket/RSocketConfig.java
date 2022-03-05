package org.thirulabs.chat.client.rsocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;

@Configuration
public class RSocketConfig {
    @Value("${chat.server.rsocket.host}")
    private String rsocketHost;

    @Value("${chat.server.rsocket.port}")
    private int rsocketPort;

    @Bean
    public RSocketRequester rSocketRequester() {
        RSocketStrategies strategies = RSocketStrategies.builder()
                .encoders(encoders -> encoders.add(new Jackson2JsonEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2JsonDecoder()))
                .build();

        RSocketRequester requester = RSocketRequester.builder()
                .rsocketStrategies(strategies)
                .tcp(rsocketHost, rsocketPort);
        return requester;
    }
}
