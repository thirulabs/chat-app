package org.thirulabs.chat.commons.dubbo;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.thirulabs.chat.commons.Message;

import java.util.List;

@Mapper
public interface DubboMessageMapper {
    DubboMessageMapper INSTANCE = Mappers.getMapper(DubboMessageMapper.class);

    Message map(DubboMessage dubboMessage);
    DubboMessage map(Message message);

    List<DubboMessage> map(List<Message> messageList);

    List<Message> asList(List<DubboMessage> messageList);
}
