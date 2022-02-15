package com.dddd.dpr_2.web.controllers;

import com.dddd.dpr_2.general.services.DoctorService;
import com.dddd.dpr_2.web.dto.DoctorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class DoctorRabbitListener {

    private final DoctorService doctorService;

    public DoctorRabbitListener(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @RabbitListener(queues = "savedDoctor")
    public void listenToInsert(Message message) {
        try {
            doctorService.create(new ObjectMapper().readValue(new String(message.getBody()), DoctorDto.class));
        } catch (Exception e) {
            log.error(e);
        }
    }

    @RabbitListener(queues = "deletedDoctor")
    public void listenToDelete(String id) {
        doctorService.deleteById(Long.parseLong(id));
    }

}
