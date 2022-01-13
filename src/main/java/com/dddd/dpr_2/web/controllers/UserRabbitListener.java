package com.dddd.dpr_2.web.controllers;

import com.dddd.dpr_2.general.services.UserService;
import com.dddd.dpr_2.web.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserRabbitListener {

	private final UserService userService;

	public UserRabbitListener(UserService userService) {
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

	@RabbitListener(queues = "saveIcon")
	public void listenToSaveIcon(Message message) throws JsonProcessingException {
		userService.saveIcon(new ObjectMapper().readValue(new String(message.getBody()), UserDto.class));
	}

	@RabbitListener(queues = "requestForIcon")
	public Message listenToRequestForIcon(String id) {
		return userService.returnIcon(Long.parseLong(id));
	}
}
