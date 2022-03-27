package org.thirulabs.chat.client.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;

/**
 * REST over HTTP/2
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnProperty(value = "client.type", havingValue = "rest-h2")
public @interface RestfulH2 {
}
