package org.thirulabs.chat.commons;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StatusMapper {
    StatusMapper INSTANCE = Mappers.getMapper(StatusMapper.class);
    Status map(org.thirulabs.chat.commons.proto.Status status);
    org.thirulabs.chat.commons.proto.Status map(Status status);
}
