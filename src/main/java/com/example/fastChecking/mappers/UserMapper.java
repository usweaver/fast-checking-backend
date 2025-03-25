package com.example.fastChecking.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.fastChecking.dto.SignUpDto;
import com.example.fastChecking.dto.UserDto;
import com.example.fastChecking.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserDto toUserDto(User user);

  @Mapping(target = "password", ignore = true)
  User signUpToUser(SignUpDto signUpDto);

}
