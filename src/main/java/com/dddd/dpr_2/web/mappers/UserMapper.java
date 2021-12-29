package com.dddd.dpr_2.web.mappers;

import com.dddd.dpr_2.database.models.Status;
import com.dddd.dpr_2.database.models.User;
import com.dddd.dpr_2.general.constants.RoleEnum;
import com.dddd.dpr_2.general.constants.StatusEnum;
import com.dddd.dpr_2.web.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

	User userDtoToUser(UserDto userDto);

	UserDto userToUserDto(User user);

	List<UserDto> userListToUserDtoList(List<User> users);

}
