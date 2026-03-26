package com.example.headless_file_sharing.repo;

import com.example.headless_file_sharing.model.FileMetadata;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@Profile("prod")
public class LocalDiskFileRepo implements FileRepo {
  private static final Path STORAGE_DIR = Paths.get("secure_storage/");

  private static Optional<Path> getFile(String accessCode) throws IOException {
    try (Stream<Path> files = Files.list(STORAGE_DIR)) {
      var file = files
          .filter(Files::isRegularFile)
          .filter(path -> path.getFileName()
              .toString()
              .contains(accessCode))
          .findFirst();

      if (file.isEmpty()) {
        System.err.println("File not found: " + accessCode);
        return Optional.empty();
      }

      System.out.println("Reading encrypted file from disk...");
      return file;
    }
  }

  @PostConstruct
  public void init() {
    try {
      Files.createDirectories(STORAGE_DIR);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize storage directory", e);
    }
  }

  @Override
  public void store(String code,
                    String encData,
                    int fileExpTimeInSec) {
    try {
      var destination = STORAGE_DIR.resolve("%s.enc-[%d]-[%s]".formatted(code, fileExpTimeInSec, Instant.now()));
      Files.writeString(destination, encData);
      System.out.println("File encrypted and saved physically to: " + destination.toAbsolutePath());
    } catch (IOException e) {
      System.err.println("Failed to write secure file: " + e.getMessage());
    }
  }

  @Override
  public Optional<String> retrieve(String accessCode) {
    try {
      var target = STORAGE_DIR.resolve(accessCode + ".enc");

      var path = getFile(accessCode);

      if (path.isPresent()) {
        return Optional.of(Files.readString(path.get()));
      }

    } catch (IOException e) {
      System.err.println("Failed to read secure file: " + e.getMessage());
    }
    return Optional.empty();
  }

  @Override
  public void wipeFromStorage(FileMetadata fileMetadata) {
    try {
      var file = getFile(fileMetadata.code());

      var deleted = Files.deleteIfExists(file.get());
      if (deleted) {
        System.out.println("Hard disk wipe successful for: " + file.get()
            .getFileName());
      } else {
        System.out.println(" File not found for wiping.");
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
