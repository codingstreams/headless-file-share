package com.example.headless_file_sharing.repository;

import java.util.Optional;

public interface FileRepository {
  void storeSecurely(String accessCode, String encryptedContent);
  void wipeFromStorage(String accessCode);
  Optional<String> retrieveSecurely(String accessCode);
}
