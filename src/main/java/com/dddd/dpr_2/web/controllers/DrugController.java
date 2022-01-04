package com.dddd.dpr_2.web.controllers;

import com.dddd.dpr_2.general.services.DrugServiceImpl;
import com.dddd.dpr_2.web.dto.DrugDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DrugController {

	private final DrugServiceImpl drugService;

	public DrugController(DrugServiceImpl drugService) {
		this.drugService = drugService;
	}
	@RabbitListener(queues = "savedDrug")
	public void listenToInsert(Message message) throws JsonProcessingException {
		drugService.create(new ObjectMapper().readValue(new String(message.getBody()), DrugDto.class));
	}

	@RabbitListener(queues = "deletedDrug")
	public void listenToDelete(String id) {
		drugService.deleteById(Long.parseLong(id));
	}
}
