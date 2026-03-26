package com.example.headless_file_sharing.repo;

import com.example.headless_file_sharing.model.FileMetadata;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Profile("dev")
public class InMemoryFileRepoImpl implements FileRepo {
  private final Map<FileMetadata, String> virtualStorage = new HashMap<>();

  @Override
  public void store(String code,
                    String encData,
                    int fileExpTimeInSec) {
    virtualStorage.put(new FileMetadata(code, fileExpTimeInSec, Instant.now()), encData);
    System.out.println("[DEV MODE] File virtually stored in HashMap. Total files: " + virtualStorage.size());
  }

  @Override
  public Optional<String> retrieve(String accessCode) {
    return Optional.of(virtualStorage.get(new FileMetadata(accessCode, -1, null)));
  }

  @Override
  public void wipeFromStorage(FileMetadata fileMetadata) {
    virtualStorage.remove(fileMetadata);
    System.out.println("[DEV MODE] File record wiped from HashMap.");
  }
}
