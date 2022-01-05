package com.dddd.dpr_2.general.services;

import com.dddd.dpr_2.database.models.User;
import com.dddd.dpr_2.database.repository.UserRepository;
import com.dddd.dpr_2.general.constants.Constants;
import com.dddd.dpr_2.general.exceptions.ResourceNotFoundException;
import com.dddd.dpr_2.web.dto.UserDto;
import com.dddd.dpr_2.web.mappers.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
@Transactional
public class UserService {

	private final UserMapper userMapper;

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserMapper userMapper,
					   UserRepository userRepository) {
		this.userMapper = userMapper;
		this.userRepository = userRepository;
	}

	public UserDto create(UserDto userDto) {
		return userMapper.userToUserDto(userRepository.save(userMapper.userDtoToUser(userDto)));
	}

	public UserDto update(UserDto userDto) {
		Optional<User> user = userRepository.findById(userDto.getId());
		return user.map(value -> userMapper.userToUserDto(userRepository
						.save(userMapper.userDtoToUser(userDto))))
				.orElseThrow(() -> new ResourceNotFoundException(Constants.PATIENT_NOT_FOUND, userDto.getId()));
	}

	public void deleteById(long id) {
		if (userRepository.findById(id).isPresent()) {
			userRepository.deleteById(id);
		}
	}

}
