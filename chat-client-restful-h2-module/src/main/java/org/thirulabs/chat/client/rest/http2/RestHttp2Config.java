package org.thirulabs.chat.client.rest.http2;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.thirulabs.chat.client.annotation.JsonEncoding;
import org.thirulabs.chat.client.annotation.ProtobufEncoding;
import org.thirulabs.chat.client.annotation.RestfulH2;
import org.thirulabs.chat.client.annotation.RestfulV1;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Configuration
@RestfulH2
public class RestHttp2Config {

    @Bean
    @Primary //quick fix - need move protobuf message converter into a common configuration
    public ProtobufHttpMessageConverter protobufHttp2MessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    @Bean
    @ProtobufEncoding
    public RestTemplate http2ProtobufTemplate(ProtobufHttpMessageConverter hmc) throws NoSuchAlgorithmException, KeyManagementException {
        RestTemplate template = new RestTemplate(Arrays.asList(hmc));
        template.setRequestFactory(requestFactory());
        return template;
    }

    @Bean
    @JsonEncoding
    public RestTemplate http2JsonRestTemplate() throws NoSuchAlgorithmException, KeyManagementException {
        return new RestTemplate(requestFactory());
    }

    private ClientHttpRequestFactory requestFactory() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager TRUST_ALL_CERTS = new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }
        };
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[] { TRUST_ALL_CERTS }, new java.security.SecureRandom());
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) TRUST_ALL_CERTS);
        builder.hostnameVerifier(((hostname, session) -> true));
        return new OkHttp3ClientHttpRequestFactory(builder.build());
    }
}
