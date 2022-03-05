package org.thirulabs.chat.server.rsocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.codec.protobuf.ProtobufDecoder;
import org.springframework.http.codec.protobuf.ProtobufEncoder;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.web.util.pattern.PathPatternRouteMatcher;
import reactor.core.publisher.Hooks;

@Configuration
@Slf4j
public class RSocketServerConfig {
    @Configuration
    static class ServerConfig {

        @Bean
        public RSocketMessageHandler rsocketMessageHandler() {
            //to avoid the on error dropped log messages when rsocket client closes the connection
            Hooks.onErrorDropped(t -> {
                log.warn("Dropped: {}", t.getMessage());
            });

            RSocketMessageHandler handler = new RSocketMessageHandler();
            handler.setRSocketStrategies(rsocketStrategies());
            return handler;
        }

        @Bean
        public RSocketStrategies rsocketStrategies() {
            return RSocketStrategies.builder()
                    .encoders(encoders -> {
                        encoders.add(new Jackson2JsonEncoder());
                        encoders.add(new ProtobufEncoder());
                    })
                    .decoders(decoders -> {
                        decoders.add(new Jackson2JsonDecoder());
                        decoders.add(new ProtobufDecoder());
                    })
                    .routeMatcher(new PathPatternRouteMatcher())
                    .build();
        }
    }
}
