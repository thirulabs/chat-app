package org.thirulabs.chat.client.rest.json;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thirulabs.chat.client.annotation.JsonEncoding;
import org.thirulabs.chat.client.annotation.RestfulV2;
import org.thirulabs.chat.client.service.ClientType;

@Service
@RestfulV2
@JsonEncoding
@Slf4j
public class MessageServiceRestV2Client extends AbstractMessageServiceRestClient {
    @Autowired
    public MessageServiceRestV2Client(Environment environment, RestTemplate restTemplate){
        super(restTemplate, environment.getProperty("chat.server.url"));
    }

    @Override
    public ClientType type() {
        return ClientType.REST_V2;
    }
}
