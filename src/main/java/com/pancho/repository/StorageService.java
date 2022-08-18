package com.pancho.repository;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void init();

    String store(String subDir, MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

    void createSubDirectory(String subDirectoryPath);

    void setRootLocation(Path rootLocation);

    List<Resource> loadFilesFromDirectory (String location) throws Exception;

    void createFolderAws(String folderName);

    void storeFilesFolderAws(String folderName, File file);
}