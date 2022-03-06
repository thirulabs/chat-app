package org.thirulabs.chat.server.dubbo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:dubbo.xml")
@ConditionalOnProperty(value = "dubbo.enabled", havingValue = "true")
public class DubboConfig {
}
