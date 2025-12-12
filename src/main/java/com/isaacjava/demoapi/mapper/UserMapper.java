package com.isaacjava.demoapi.mapper;

import com.isaacjava.demoapi.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", ignore = true)
    User registerDtoToUser(com.isaacjava.demoapi.dto.RegisterDto registerDto);
}
