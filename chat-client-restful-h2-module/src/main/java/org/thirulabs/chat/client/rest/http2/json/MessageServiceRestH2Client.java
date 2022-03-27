package org.thirulabs.chat.client.rest.http2.json;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thirulabs.chat.client.annotation.JsonEncoding;
import org.thirulabs.chat.client.annotation.RestfulH2;
import org.thirulabs.chat.client.service.ClientType;
import org.thirulabs.chat.client.service.MessageServiceClient;
import org.thirulabs.chat.commons.Message;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RestfulH2
@JsonEncoding
@RequiredArgsConstructor
@Slf4j
public class MessageServiceRestH2Client implements MessageServiceClient {
    private final Environment environment;
    private final RestTemplate restTemplate;
    private String chatServerUrl;
    private static final String MESSAGE_URI_FORMAT = "%s/message";
    private static final String MESSAGE_ID_URI_FORMAT = MESSAGE_URI_FORMAT + "/%s";


    @PostConstruct
    void init(){
        chatServerUrl = environment.getProperty("chat.server.secure.url");
    }

    @Override
    public ClientType type() {
        return ClientType.REST;
    }

    @Override
    public Optional<Message> findById(Long id) {
        String url = String.format(MESSAGE_ID_URI_FORMAT,chatServerUrl, id);
        ResponseEntity<Message> messageEntity = restTemplate.getForEntity(url, Message.class);
        if(messageEntity.getStatusCode().is2xxSuccessful()){
            return Optional.of(messageEntity.getBody());
        }else {
            log.debug("findById failed with code {}", messageEntity.getStatusCode());
            return Optional.empty();
        }
    }

    @Override
    public List<Message> findAll() {
        String url = String.format(MESSAGE_URI_FORMAT,chatServerUrl);
        ResponseEntity<Message[]> messageEntity = restTemplate.getForEntity(url, Message[].class);
        if(messageEntity.getStatusCode().is2xxSuccessful()){
            return Arrays.asList(messageEntity.getBody());
        }else {
            log.debug("findAll failed with code {}", messageEntity.getStatusCode());
            return Collections.emptyList();
        }
    }

    @Override
    public Message add(Message message) {
        HttpEntity<Message> messageEntity = new HttpEntity<>(message);
        String url = String.format(MESSAGE_URI_FORMAT,chatServerUrl);
        ResponseEntity<Message> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, messageEntity, Message.class);
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            return responseEntity.getBody();
        }else {
            log.warn("add failed with code {}", responseEntity.getStatusCode());
            return null;
        }
    }

    @Override
    public boolean update(Long id, Message message) {
        HttpEntity<Message> messageEntity = new HttpEntity<>(message);
        String url = String.format(MESSAGE_ID_URI_FORMAT,chatServerUrl, id);
        ResponseEntity<Message> responseEntity = restTemplate.postForEntity(url, messageEntity, Message.class);
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            return true;
        }else {
            log.warn("update failed with code {}", responseEntity.getStatusCode());
            return false;
        }
    }

    @Override
    public boolean remove(Long id) {
        String url = String.format(MESSAGE_ID_URI_FORMAT,chatServerUrl, id);
        restTemplate.delete(url);
        return true; //TODO use exchange and inspect response entity for status code
    }

    @Override
    public void removeAll() {
        String url = String.format(MESSAGE_URI_FORMAT,chatServerUrl);
        restTemplate.delete(url);
    }
}
