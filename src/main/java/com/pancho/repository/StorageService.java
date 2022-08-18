package com.pancho.repository;

import java.io.File;

public interface StorageService {

    void storeFilesFolderAws(String folderName, File file);
}