package com.dev.springboottesting.mappers;

import com.dev.springboottesting.dto.UserDto;
import com.dev.springboottesting.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(implementationName = "GenericMapper.class")
public interface Converter extends GenericMapper<User, UserDto>{
    @Override
    @Mapping(target = "id", ignore = true)
    User asEntity(UserDto dto);
}
