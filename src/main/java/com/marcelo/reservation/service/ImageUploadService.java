package com.marcelo.reservation.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageUploadService {
    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    private final AmazonS3 s3Client;
    private String fileName;
    @Transactional
    public String uploadFile(String location, final MultipartFile multipartFile) {
        try {
            final File file = convertMultiPartFileToFile(multipartFile);
            fileName = uploadFileToS3Bucket(location, bucketName, file);
            file.deleteOnExit();

        } catch (final AmazonServiceException ex) {
            System.out.println("Error while uploading file = "+ex.getMessage());
        }

        return fileName;

    }
    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {

        final File file = new File(multipartFile.getOriginalFilename());

        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        }
        catch (final IOException ex) {
            System.out.println("Error converting the multi-part file to file= "+ex.getMessage());
        }
        return file;
    }

    @Transactional
    private String uploadFileToS3Bucket(String location, final String bucketName, final File file) {
        String fileName = location+file.getName();
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
        s3Client.putObject(putObjectRequest);
        return fileName;
    }

    @Transactional
    public String deleteFileFromS3Bucket(String fileName) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, fileName);
        s3Client.deleteObject(deleteObjectRequest);
        return fileName;
    }
}