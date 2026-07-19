package com.duoc.bff.controller;

import com.duoc.bff.service.StorageClientService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

// La verificacion de rol "profesor" ya ocurrio en SecurityConfig
// (.requestMatchers("/api/s3/**").hasRole("profesor")) antes de
// que la peticion llegue hasta aca.
@RestController
@RequestMapping("/api/s3")
public class S3Controller {

    private final StorageClientService storageClientService;

    public S3Controller(StorageClientService storageClientService) {
        this.storageClientService = storageClientService;
    }

    @PostMapping("/upload/{numeroResumen}")
    public ResponseEntity<String> subirArchivo(
            @PathVariable String numeroResumen,
            @RequestParam("archivo") MultipartFile archivo)
            throws Exception {

        return ResponseEntity.ok(
                storageClientService.subirArchivo(numeroResumen, archivo)
        );
    }

    @PutMapping("/update/{numeroResumen}")
    public ResponseEntity<String> modificarArchivo(
            @PathVariable String numeroResumen,
            @RequestParam("archivo") MultipartFile archivo)
            throws Exception {

        return ResponseEntity.ok(
                storageClientService.modificarArchivo(numeroResumen, archivo)
        );
    }

    @GetMapping("/download/{numeroResumen}")
    public ResponseEntity<byte[]> descargarArchivo(
            @PathVariable String numeroResumen,
            @RequestParam String nombreArchivo) {

        byte[] archivo = storageClientService.descargarArchivo(numeroResumen, nombreArchivo);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivo)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(archivo);
    }

    @DeleteMapping("/delete/{numeroResumen}")
    public ResponseEntity<String> borrarArchivo(
            @PathVariable String numeroResumen,
            @RequestParam String nombreArchivo) {

        return ResponseEntity.ok(
                storageClientService.borrarArchivo(numeroResumen, nombreArchivo)
        );
    }
}
