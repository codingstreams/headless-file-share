package com.example.headless_file_sharing.service.download;

import com.example.headless_file_sharing.repository.FileRepository;
import com.example.headless_file_sharing.security.EncryptionStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class LocalFileDownloadService implements DownloadService{

  private final EncryptionStrategy defaultEncryption;
  private final EncryptionStrategy secureEncryption;
  private final ApplicationEventPublisher eventPublisher;
  private final FileRepository fileRepository;

  public LocalFileDownloadService(
      EncryptionStrategy defaultEncryption,
      @Qualifier("aesEncryption") EncryptionStrategy secureEncryption,
      ApplicationEventPublisher eventPublisher,
      FileRepository fileRepository) {
    this.defaultEncryption = defaultEncryption;
    this.secureEncryption = secureEncryption;
    this.eventPublisher = eventPublisher;
    this.fileRepository = fileRepository;
  }

  @Override
  public boolean processDownload(String accessCode, String destinationPath, boolean isFastEncryption) {
    var encryptedContent = fileRepository.retrieveSecurely(accessCode);

    if (encryptedContent.isEmpty()) {
      System.out.println("Error: No file found for access code " + accessCode + ". It may have expired.");
      return false;
    }

    var strategy = isFastEncryption ?  defaultEncryption: secureEncryption;
    var decryptedData = strategy.decryptData(encryptedContent.get());

    try {
      Files.writeString(Paths.get(destinationPath), decryptedData);
      System.out.println("Success! File decrypted and saved to: " + destinationPath);
      return true;
    } catch (IOException e) {
      System.out.println("Error writing file to destination: " + destinationPath);
      return false;
    }
  }
}
