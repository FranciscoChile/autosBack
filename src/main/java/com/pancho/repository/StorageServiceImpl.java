package com.pancho.repository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class StorageServiceImpl implements StorageService {

    private Path rootLocation;

    @Autowired
    public StorageServiceImpl(ImagesStorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }
    }

    @Override
    public void createSubDirectory(String subDirectoryPath) {
        try {
            Files.createDirectories(
                Paths.get(rootLocation.toString() + File.separator + subDirectoryPath));
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }        
    }

    @Override
    public String store(String subDir, MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }

            if (subDir.length() == 0) {
                throw new StorageException("Sub directory to store is empty " + subDir);
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(subDir + File.separator + filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }

        return filename;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }


public List<Resource> loadFilesFromDirectory (String location) throws Exception {

    List<Resource> list = new ArrayList<>();

    File directoryPath = new File(this.rootLocation + "/" + location);
    File filesList[] = directoryPath.listFiles();

    for(File file : filesList) {
        Path path = load(file.getAbsolutePath());
        Resource resource = new UrlResource(path.toUri());
        if (resource.exists() || resource.isReadable()) {            
            list.add(resource);
        }
    }
    
    return list;
}

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new FileNotFoundException(
                        "Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public Path getRootLocation() {
        return rootLocation;
    }

    public void setRootLocation(Path rootLocation) {
        this.rootLocation = rootLocation;
    }

    public void createFolderAws(String folderName) {

        String bucketName = "mubefotos";
                 
        S3Client client = S3Client.builder().build();
         
        PutObjectRequest request = PutObjectRequest.builder()
                        .bucket(bucketName).key(folderName).build();
         
        client.putObject(request, RequestBody.empty());

    }

    @Override
    public void storeFilesFolderAws(String folderName, File file) {        
        
        String bucketName = "mubefotos";
        String fileName="";

        fileName = folderName + "/" + file.getName();

        S3Client client = S3Client.builder().build();
         
        PutObjectRequest request = PutObjectRequest.builder()
                            .bucket(bucketName).key(fileName).build();

        client.putObject(request, RequestBody.fromFile(file));
    }


    
}
