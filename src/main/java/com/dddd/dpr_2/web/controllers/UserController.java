package com.dddd.dpr_2.web.controllers;

import com.dddd.dpr_2.general.services.UserServiceImpl;
import com.dddd.dpr_2.web.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserController {

	private final UserServiceImpl userService;

	public UserController(UserServiceImpl userService) {
		this.userService = userService;
	}

	@RabbitListener(queues = "savedUser")
	public void listenToInsert(Message message) throws JsonProcessingException {
		userService.create(new ObjectMapper().readValue(new String(message.getBody()), UserDto.class));
	}

	@RabbitListener(queues = "deletedUser")
	public void listenToDelete(String id) {
		userService.deleteById(Long.parseLong(id));
	}

}
