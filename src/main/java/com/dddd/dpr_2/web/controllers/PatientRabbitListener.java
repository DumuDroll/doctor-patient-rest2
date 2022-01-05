package com.dddd.dpr_2.web.controllers;

import com.dddd.dpr_2.general.services.PatientService;
import com.dddd.dpr_2.web.dto.PatientDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class PatientRabbitListener {

	private final PatientService patientService;

	public PatientRabbitListener(PatientService patientService) {
		this.patientService = patientService;
	}

	@RabbitListener(queues = "savedPatient")
	public void listenToInsert(Message message) throws JsonProcessingException {
		patientService.create(new ObjectMapper().readValue(new String(message.getBody()), PatientDto.class));
	}

	@RabbitListener(queues = "updatedPatient")
	public void listenToUpdate(Message message) {
		try {
			patientService.update(new ObjectMapper().readValue(new String(message.getBody()), PatientDto.class));
		} catch (Exception e){
			log.error(e.getMessage());
		}
	}

	@RabbitListener(queues = "deletedPatient")
	public void listenToDelete(String id) {
		patientService.deleteById(Long.parseLong(id));
	}
}
