package com.duoc.msstorage.controller;

import com.duoc.msstorage.service.S3Service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

// Servicio interno: no valida JWT ni roles. El BFF ya verifico que el
// usuario tiene rol "profesor" antes de reenviar la peticion hasta aca.
@RestController
@RequestMapping("/api/s3")
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload/{numeroResumen}")
    public ResponseEntity<String> subirArchivo(
            @PathVariable String numeroResumen,
            @RequestParam("archivo") MultipartFile archivo)
            throws Exception {

        return ResponseEntity.ok(
                s3Service.subirArchivo(numeroResumen, archivo)
        );
    }

    @PutMapping("/update/{numeroResumen}")
    public ResponseEntity<String> modificarArchivo(
            @PathVariable String numeroResumen,
            @RequestParam("archivo") MultipartFile archivo)
            throws Exception {

        return ResponseEntity.ok(
                s3Service.modificarArchivo(numeroResumen, archivo)
        );
    }

    @GetMapping("/download/{numeroResumen}")
    public ResponseEntity<byte[]> descargarArchivo(
            @PathVariable String numeroResumen,
            @RequestParam String nombreArchivo) {

        byte[] archivo =
                s3Service.descargarArchivo(
                        numeroResumen,
                        nombreArchivo
                );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + nombreArchivo
                )
                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM
                )
                .body(archivo);
    }

    @DeleteMapping("/delete/{numeroResumen}")
    public ResponseEntity<String> borrarArchivo(
            @PathVariable String numeroResumen,
            @RequestParam String nombreArchivo) {

        return ResponseEntity.ok(
                s3Service.borrarArchivo(
                        numeroResumen,
                        nombreArchivo
                )
        );
    }
}
