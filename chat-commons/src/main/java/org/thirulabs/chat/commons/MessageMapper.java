package org.thirulabs.chat.commons;

import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    default Date map(Timestamp ts) {
        return new Date(Timestamps.toMillis(ts));
    }

    default Timestamp map(Date date){
        return Timestamps.fromDate(date);
    }

    @Mapping(source = "messageID", target = "id")
    @Mapping(source = "createdTS", target = "createdTs")
    Message map(org.thirulabs.chat.commons.proto.Message message);

    @Mapping(target = "messageID", source = "id")
    @Mapping(target = "createdTS", source = "createdTs")
    org.thirulabs.chat.commons.proto.Message map(Message message);

    List<org.thirulabs.chat.commons.proto.Message> map(List<Message> list);
}
