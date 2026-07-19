package com.duoc.bff.service;

import com.duoc.bff.config.RabbitMQConfig;
import com.duoc.bff.model.InscripcionResumen;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductorService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarResumen(InscripcionResumen resumen) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.INSCRIPCIONES_QUEUE, resumen);
    }
}
