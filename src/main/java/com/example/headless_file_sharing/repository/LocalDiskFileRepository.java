package com.example.headless_file_sharing.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Repository
@Profile("prod")
public class LocalDiskFileRepository implements FileRepository{
  private final Path storageDir = Paths.get("secure_storage/");

  @PostConstruct
  public void init(){
    try {
      Files.createDirectories(storageDir);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize storage directory", e);
    }
  }

  @Override
  public void storeSecurely(String accessCode, String encryptedContent) {
    try {
      var destination = storageDir.resolve(accessCode + ".enc");
      Files.writeString(destination, encryptedContent);
      System.out.println("[PROD MODE] File encrypted and saved physically to: " + destination.toAbsolutePath());
    } catch (IOException e) {
      System.err.println("Failed to write secure file: " + e.getMessage());
    }
  }

  @Override
  public void wipeFromStorage(String accessCode) {
    try {
      var target = storageDir.resolve(accessCode + ".enc");
      var deleted = Files.deleteIfExists(target);
      if (deleted) {
        System.out.println("[PROD MODE] Hard disk wipe successful for: " + target.getFileName());
      } else {
        System.out.println("[PROD MODE] File not found for wiping.");
      }
    } catch (IOException e) {
      System.err.println("Failed to delete secure file: " + e.getMessage());
    }
  }

  @Override
  public Optional<String> retrieveSecurely(String accessCode) {
    try {
      var target = storageDir.resolve(accessCode + ".enc");
      if (Files.exists(target)) {
        System.out.println("[PROD MODE] Reading encrypted file from disk...");
        return Optional.of(Files.readString(target));
      }
    } catch (IOException e) {
      System.err.println("Failed to read secure file: " + e.getMessage());
    }
    return Optional.empty();
  }
}
