package org.thirulabs.chat.server.rest;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatHttpConnectorConfiguration {
    @Value("${http.port}")
    private int httpPort;

    @Bean
    public ServletWebServerFactory serverFactory() {
        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        Connector httpConnector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        httpConnector.setPort(httpPort);
        serverFactory.addAdditionalTomcatConnectors(httpConnector);
        return serverFactory;
    }
}
