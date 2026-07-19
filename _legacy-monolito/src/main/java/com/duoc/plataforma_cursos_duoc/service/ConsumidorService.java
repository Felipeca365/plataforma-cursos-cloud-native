package com.duoc.plataforma_cursos_duoc.service;

import com.duoc.plataforma_cursos_duoc.config.RabbitMQConfig;
import com.duoc.plataforma_cursos_duoc.model.InscripcionResumen;
import com.duoc.plataforma_cursos_duoc.model.InscripcionResumenEntity;
import com.duoc.plataforma_cursos_duoc.repository.InscripcionResumenRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumidorService {

    @Autowired
    private InscripcionResumenRepository inscripcionResumenRepository;

    @RabbitListener(queues = RabbitMQConfig.INSCRIPCIONES_QUEUE)
    public void recibirResumen(InscripcionResumen resumen) {

        System.out.println("Mensaje recibido de la cola: " + resumen.getEstudiante());

        InscripcionResumenEntity entity = new InscripcionResumenEntity(
                resumen.getEstudiante(),
                resumen.getTotalPagar()
        );

        inscripcionResumenRepository.save(entity);

        System.out.println("Resumen guardado en Oracle Cloud con id: " + entity.getId());
    }
}