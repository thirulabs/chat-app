package org.thirulabs.chat.client.rest.protobuf;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thirulabs.chat.client.service.ClientType;
import org.thirulabs.chat.client.service.MessageServiceClient;
import org.thirulabs.chat.commons.Message;
import org.thirulabs.chat.commons.MessageMapper;
import org.thirulabs.chat.client.annotation.ProtobufEncoding;
import org.thirulabs.chat.client.annotation.Restful;
import org.thirulabs.chat.commons.proto.MessageArray;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Restful
@ProtobufEncoding
@RequiredArgsConstructor
@Slf4j
public class MessageServiceRestClient implements MessageServiceClient {
    private final Environment environment;
    private final RestTemplate restTemplate;

    private String chatServerUrl;
    private static final String MESSAGE_URI_FORMAT = "%s/proto/message";
    private static final String MESSAGE_ID_URI_FORMAT = MESSAGE_URI_FORMAT + "/%s";


    @PostConstruct
    void init(){
        chatServerUrl = environment.getProperty("chat.server.url");
    }

    @Override
    public String type() {
        return ClientType.REST_PROTOBUF;
    }

    @Override
    public Optional<Message> findById(Long id) {
        String url = String.format(MESSAGE_ID_URI_FORMAT,chatServerUrl, id);
        var messageEntity = restTemplate.getForEntity(url, org.thirulabs.chat.commons.proto.Message.class);
        if(messageEntity.getStatusCode().is2xxSuccessful()){
            return Optional.of(MessageMapper.INSTANCE.map(messageEntity.getBody()));
        }else {
            log.debug("findById failed with code {}", messageEntity.getStatusCode());
            return Optional.empty();
        }
    }

    @Override
    public List<Message> findAll() {
        String url = String.format(MESSAGE_URI_FORMAT,chatServerUrl);
        var messageEntity = restTemplate.getForEntity(url, MessageArray.class);
        if(messageEntity.getStatusCode().is2xxSuccessful()){
            var array = messageEntity.getBody();
            if(array!=null) {
                return MessageMapper.INSTANCE.asList(array.getArrayList());
            }else {
                return Collections.emptyList();
            }
        }else {
            log.debug("findAll failed with code {}", messageEntity.getStatusCode());
            return Collections.emptyList();
        }
    }

    @Override
    public Message add(Message message) {
        var messageEntity = new HttpEntity<org.thirulabs.chat.commons.proto.Message>(MessageMapper.INSTANCE.map(message));
        String url = String.format(MESSAGE_URI_FORMAT,chatServerUrl);
        var responseEntity = restTemplate.exchange(url, HttpMethod.PUT, messageEntity, org.thirulabs.chat.commons.proto.Message.class);
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            return MessageMapper.INSTANCE.map(responseEntity.getBody());
        }else {
            log.warn("add failed with code {}", responseEntity.getStatusCode());
            return null;
        }
    }

    @Override
    public boolean update(Long id, Message message) {
        var messageEntity = new HttpEntity<org.thirulabs.chat.commons.proto.Message>(MessageMapper.INSTANCE.map(message));
        String url = String.format(MESSAGE_ID_URI_FORMAT,chatServerUrl, id);
        var responseEntity = restTemplate.postForEntity(url, messageEntity, org.thirulabs.chat.commons.proto.Message.class);
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
