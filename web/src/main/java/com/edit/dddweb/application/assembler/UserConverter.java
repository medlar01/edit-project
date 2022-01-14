package com.edit.dddweb.application.assembler;

import com.edit.dddweb.infrastructure.entity.User;
import com.edit.dddweb.interfaces.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConverter {
    UserConverter INST = Mappers.getMapper(UserConverter.class);
    UserDTO toUserDTO(User user);
    List<UserDTO> toUserDTOs(List<User> users);
}
