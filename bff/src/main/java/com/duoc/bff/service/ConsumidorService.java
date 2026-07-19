package com.duoc.bff.service;

import com.duoc.bff.config.RabbitMQConfig;
import com.duoc.bff.model.InscripcionResumen;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumidorService {

    @Autowired
    private InscripcionesClientService inscripcionesClientService;

    // Este listener corre en background, escuchando la cola desde que el
    // BFF arranca. Es el "consumidor" real de RabbitMQ: apenas el
    // ProductorService publica un mensaje, este metodo lo recibe solo,
    // sin que nadie tenga que llamarlo.
    @RabbitListener(queues = RabbitMQConfig.INSCRIPCIONES_QUEUE)
    public void recibirResumen(InscripcionResumen resumen) {

        System.out.println("BFF: mensaje recibido de la cola para: " + resumen.getEstudiante());

        inscripcionesClientService.persistirResumen(resumen);

        System.out.println("BFF: resumen reenviado a ms-inscripciones para persistir en Oracle");
    }
}
