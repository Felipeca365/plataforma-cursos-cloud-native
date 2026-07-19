package com.duoc.bff.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageClientService {

    private final RestTemplate restTemplate;

    @Value("${ms.storage.url}")
    private String msStorageUrl;

    public StorageClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String subirArchivo(String numeroResumen, MultipartFile archivo) throws Exception {
        return reenviarMultipart(msStorageUrl + "/api/s3/upload/" + numeroResumen, HttpMethod.POST, archivo);
    }

    public String modificarArchivo(String numeroResumen, MultipartFile archivo) throws Exception {
        return reenviarMultipart(msStorageUrl + "/api/s3/update/" + numeroResumen, HttpMethod.PUT, archivo);
    }

    public byte[] descargarArchivo(String numeroResumen, String nombreArchivo) {
        String url = msStorageUrl + "/api/s3/download/" + numeroResumen + "?nombreArchivo=" + nombreArchivo;
        ResponseEntity<byte[]> respuesta = restTemplate.getForEntity(url, byte[].class);
        return respuesta.getBody();
    }

    public String borrarArchivo(String numeroResumen, String nombreArchivo) {
        String url = msStorageUrl + "/api/s3/delete/" + numeroResumen + "?nombreArchivo=" + nombreArchivo;
        ResponseEntity<String> respuesta = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        return respuesta.getBody();
    }

    private String reenviarMultipart(String url, HttpMethod metodo, MultipartFile archivo) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ByteArrayResource recurso = new ByteArrayResource(archivo.getBytes()) {
            @Override
            public String getFilename() {
                return archivo.getOriginalFilename();
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("archivo", recurso);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> respuesta = restTemplate.exchange(url, metodo, request, String.class);
        return respuesta.getBody();
    }
}
