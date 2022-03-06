package org.thirulabs.chat.commons.dubbo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DubboMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String message;
    private Date createdTs;
    private int likes;
}
