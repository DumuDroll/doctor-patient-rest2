package com.dddd.dpr_2.web.controllers;

import com.dddd.dpr_2.general.services.DoctorServiceImpl;
import com.dddd.dpr_2.web.dto.DoctorDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DoctorController {

	private final DoctorServiceImpl doctorService;

	public DoctorController(DoctorServiceImpl doctorService) {
		this.doctorService = doctorService;
	}

	@RabbitListener(queues = "savedDoctor")
	public void listenToInsert(Message message) throws JsonProcessingException {
		doctorService.create(new ObjectMapper().readValue(new String(message.getBody()), DoctorDto.class));
	}

	@RabbitListener(queues = "deletedDoctor")
	public void listenToDelete(String id) {
		doctorService.deleteById(Long.parseLong(id));
	}

}
