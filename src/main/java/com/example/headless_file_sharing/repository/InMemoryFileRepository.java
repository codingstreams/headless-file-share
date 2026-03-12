package com.example.headless_file_sharing.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Profile("dev")
public class InMemoryFileRepository implements FileRepository{
  private final Map<String, String> virtualStorage = new HashMap<>();


  @Override
  public void storeSecurely(String accessCode, String encryptedContent) {
    virtualStorage.put(accessCode, encryptedContent);
    System.out.println("[DEV MODE] File virtually stored in HashMap. Total files: " + virtualStorage.size());
  }

  @Override
  public void wipeFromStorage(String accessCode) {
    virtualStorage.remove(accessCode);
    System.out.println("[DEV MODE] File record wiped from HashMap.");
  }

  @Override
  public Optional<String> retrieveSecurely(String accessCode) {
    return Optional.of(virtualStorage.get(accessCode));
  }
}
