package com.dddd.dpr_2.web.controllers;

import com.dddd.dpr_2.general.services.DrugService;
import com.dddd.dpr_2.web.dto.DrugDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class DrugRabbitListener {

    private final DrugService drugService;

    public DrugRabbitListener(DrugService drugService) {
        this.drugService = drugService;
    }

    @RabbitListener(queues = "savedDrug")
    public void listenToInsert(Message message) {
        try {
            drugService.create(new ObjectMapper().readValue(new String(message.getBody()), DrugDto.class));
        } catch (Exception e) {
            log.error(e);
        }
    }

    @RabbitListener(queues = "deletedDrug")
    public void listenToDelete(String id) {
        drugService.deleteById(Long.parseLong(id));
    }
}
