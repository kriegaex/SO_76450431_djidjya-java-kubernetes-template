package com.djidjya.team.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileService {

    private final AmazonS3 amazonS3;

    @Value("${spring.aws.s3.bucket}")
    private String bucketName;

    public FileService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }


    public void saveFile(String content) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String fileName = String.format("reports/db-report-%s.html", LocalDateTime.now().format(formatter));
        if (!amazonS3.doesObjectExist(bucketName, fileName)) {
            amazonS3.putObject(bucketName,
                    fileName,
                    new ByteArrayInputStream(content.getBytes()),
                    new ObjectMetadata()
            );
        }
    }
}
