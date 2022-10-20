package com.mube.repository;

import java.io.File;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class StorageServiceImpl implements StorageService {

    @Override
    public void storeFilesFolderAws(String folderName, File file) {        
        
        //region sa-east-1

        String bucketName = "mubefotos";
        String fileName="";

        fileName = folderName + "/" + file.getName();

        S3Client client = S3Client.builder().build();
        
        PutObjectRequest request = PutObjectRequest.builder()
                            .bucket(bucketName).key(fileName).build();

        client.putObject(request, RequestBody.fromFile(file));
    }


    
}
