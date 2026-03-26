package com.example.headless_file_sharing.repo;

import com.example.headless_file_sharing.model.FileMetadata;

import java.util.Optional;

public interface FileRepo {
  void store(String code,
             String encData,
             int fileExpTimeInSec);

  Optional<String> retrieve(String accessCode);

  void wipeFromStorage(FileMetadata fileMetadata);
}
