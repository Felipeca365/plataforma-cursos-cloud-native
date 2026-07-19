package com.duoc.msstorage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;

import software.amazon.awssdk.services.s3.S3Client;

import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String subirArchivo(String numeroResumen,
                               MultipartFile archivo) throws Exception {

        String key = numeroResumen + "/"
                + archivo.getOriginalFilename();

        PutObjectRequest request =
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType(archivo.getContentType())
                        .build();

        s3Client.putObject(
                request,
                RequestBody.fromBytes(archivo.getBytes())
        );

        return "Archivo subido correctamente";
    }

    public String modificarArchivo(String numeroResumen,
                                   MultipartFile archivo) throws Exception {

        return subirArchivo(numeroResumen, archivo);
    }

    public byte[] descargarArchivo(String numeroResumen,
                                   String nombreArchivo) {

        String key = numeroResumen + "/" + nombreArchivo;

        GetObjectRequest request =
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build();

        ResponseBytes<GetObjectResponse> response =
                s3Client.getObjectAsBytes(request);

        return response.asByteArray();
    }

    public String borrarArchivo(String numeroResumen,
                                String nombreArchivo) {

        String key = numeroResumen + "/" + nombreArchivo;

        DeleteObjectRequest request =
                DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build();

        s3Client.deleteObject(request);

        return "Archivo eliminado correctamente";
    }
}
