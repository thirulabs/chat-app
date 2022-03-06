package org.thirulabs.chat.commons;

public interface MessagePaths {
    String JSON_PREFIX = "json.";
    String PROTO_PREFIX = "proto.";

    String ADD_MESSAGE = "message.add";
    String UPDATE_MESSAGE = "message.update";
    String REMOVE_MESSAGE_BY_ID = "message.remove.by.id";
    String REMOVE_ALL_MESSAGES = "message.remove.all";
    String FIND_MESSAGE_BY_ID = "message.find.by.id";
    String FIND_ALL_MESSAGES = "message.find.all";

}
