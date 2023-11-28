package com.webflux.reactivesecurity.mapper;

import com.webflux.reactivesecurity.dto.UserDto;
import com.webflux.reactivesecurity.entity.UserEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto map(UserEntity userEntity);
    @InheritInverseConfiguration
    UserEntity map(UserDto dto);
}
