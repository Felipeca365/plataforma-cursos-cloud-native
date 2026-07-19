package com.duoc.bff.service;

import com.duoc.bff.model.Curso;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CursosClientService {

    private final RestTemplate restTemplate;

    @Value("${ms.cursos.url}")
    private String msCursosUrl;

    public CursosClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Curso> listarCursos() {
        Curso[] cursos = restTemplate.getForObject(msCursosUrl + "/api/cursos", Curso[].class);
        return cursos != null ? List.of(cursos) : List.of();
    }
}
