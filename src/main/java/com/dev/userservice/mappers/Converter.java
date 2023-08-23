package com.dev.userservice.mappers;

import com.dev.userservice.dto.UserDto;
import com.dev.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(implementationName = "GenericMapper.class")
public interface Converter extends GenericMapper<User, UserDto>{
    @Override
    @Mapping(target = "id", ignore = true)
    User asEntity(UserDto dto);
}
