package org.thirulabs.chat.client.dubbo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.thirulabs.chat.client.annotation.Dubbo;

@Configuration
@Dubbo
@ImportResource("classpath:dubbo.xml")
public class DubboClientConfig {
}
