package org.thirulabs.chat.client.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;
import org.thirulabs.chat.client.annotation.JsonEncoding;
import org.thirulabs.chat.client.annotation.ProtobufEncoding;
import org.thirulabs.chat.client.annotation.RestfulV1;
import org.thirulabs.chat.client.annotation.RestfulV2;

import java.util.Arrays;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RestConfig {
    private final CloseableHttpClient httpClient;

    @Bean
    public ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    @Bean
    @ProtobufEncoding
    @RestfulV1
    public RestTemplate restV1ProtobufTemplate(ProtobufHttpMessageConverter hmc) {
        return new RestTemplate(Arrays.asList(hmc));
    }

    @Bean
    @ProtobufEncoding
    @RestfulV2
    public RestTemplate restV2ProtobufTemplate(ProtobufHttpMessageConverter hmc) {
        RestTemplate restTemplate = new RestTemplate(Arrays.asList(hmc));
        restTemplate.setRequestFactory(clientHttpRequestFactory());
        return  restTemplate;
    }

    @Bean
    @RestfulV1
    @JsonEncoding
    public RestTemplate restV1JsonTemplate() {
        return new RestTemplate();
    }

    @Bean
    @RestfulV2
    @JsonEncoding
    public RestTemplate restJsonTemplate() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        return restTemplate;
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient);
        return clientHttpRequestFactory;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("poolScheduler");
        scheduler.setPoolSize(50);
        return scheduler;
    }
}
