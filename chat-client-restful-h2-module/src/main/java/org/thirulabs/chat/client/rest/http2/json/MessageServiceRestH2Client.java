package org.thirulabs.chat.client.rest.http2.json;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thirulabs.chat.client.annotation.JsonEncoding;
import org.thirulabs.chat.client.annotation.RestfulH2;
import org.thirulabs.chat.client.rest.json.AbstractMessageServiceRestClient;
import org.thirulabs.chat.client.service.ClientType;

@Service
@RestfulH2
@JsonEncoding
@Slf4j
public class MessageServiceRestH2Client extends AbstractMessageServiceRestClient {

    @Autowired
    public MessageServiceRestH2Client(Environment environment, RestTemplate restTemplate){
        super(restTemplate, environment.getProperty("chat.server.secure.url"));
    }

    @Override
    public ClientType type() {
        return ClientType.REST_H2;
    }

}
