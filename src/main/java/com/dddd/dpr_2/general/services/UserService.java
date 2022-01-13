package com.dddd.dpr_2.general.services;

import com.dddd.dpr_2.database.models.User;
import com.dddd.dpr_2.database.repository.UserRepository;
import com.dddd.dpr_2.web.dto.UserDto;
import com.dddd.dpr_2.web.mappers.UserMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
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

	public void create(UserDto userDto) {
		userMapper.userToUserDto(userRepository.save(userMapper.userDtoToUser(userDto)));
	}

	public void deleteById(long id) {
		if (userRepository.findById(id).isPresent()) {
			userRepository.deleteById(id);
		}
	}

	public void saveIcon(UserDto userDto) {
		userRepository.save(userMapper.userDtoToUser(userDto));
	}

	public Message returnIcon(long id) {
		UserDto userDto = new UserDto();
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			userDto = userMapper.userToUserDto(optionalUser.get());
		}
		try {
			ObjectMapper mapper = JsonMapper.builder()
					.findAndAddModules()
					.build();
			return MessageBuilder
					.withBody(mapper.writeValueAsString(userDto).getBytes(StandardCharsets.UTF_8))
					.setContentType(MessageProperties.CONTENT_TYPE_JSON)
					.build();
		} catch (JsonProcessingException jsonProcessingException) {
			return MessageBuilder
					.withBody("".getBytes(StandardCharsets.UTF_8))
					.setContentType(MessageProperties.CONTENT_TYPE_JSON)
					.build();
		}
	}
}
