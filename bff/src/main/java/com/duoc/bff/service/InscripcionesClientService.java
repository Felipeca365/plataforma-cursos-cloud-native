package com.duoc.bff.service;

import com.duoc.bff.model.InscripcionResumen;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class InscripcionesClientService {

    private final RestTemplate restTemplate;

    @Value("${ms.inscripciones.url}")
    private String msInscripcionesUrl;

    public InscripcionesClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Llamado desde el ConsumidorService, despues de recibir el mensaje de RabbitMQ.
    public void persistirResumen(InscripcionResumen resumen) {
        restTemplate.postForObject(
                msInscripcionesUrl + "/api/inscripciones",
                resumen,
                Object.class
        );
    }

    // Para el endpoint GET del BFF: evidencia de lo ya procesado.
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> listarResumenes() {
        Map[] resultado = restTemplate.getForObject(
                msInscripcionesUrl + "/api/inscripciones",
                Map[].class
        );
        return resultado != null ? List.of(resultado) : List.of();
    }
}
