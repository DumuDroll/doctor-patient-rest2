package com.dddd.dpr_2.web.mappers;

import com.dddd.dpr_2.database.models.User;
import com.dddd.dpr_2.web.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

	User userDtoToUser(UserDto userDto);

	UserDto userToUserDto(User user);

	List<UserDto> userListToUserDtoList(List<User> users);

}
