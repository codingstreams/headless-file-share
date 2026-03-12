package com.example.headless_file_sharing.service.upload;

import com.example.headless_file_sharing.events.FileExpiredEvent;
import com.example.headless_file_sharing.repository.FileRepository;
import com.example.headless_file_sharing.security.EncryptionStrategy;
import com.example.headless_file_sharing.service.accesscode.AccessCodeGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class LocalFileUploadService implements UploadService {
  private final EncryptionStrategy defaultEncryption;
  private final EncryptionStrategy secureEncryption;
  private final AccessCodeGenerator codeGenerator;
  private final ApplicationEventPublisher eventPublisher;
  private final FileRepository fileRepository;

  public LocalFileUploadService(
      EncryptionStrategy defaultEncryption,
      @Qualifier("aesEncryption") EncryptionStrategy secureEncryption,
      AccessCodeGenerator codeGenerator,
    ApplicationEventPublisher eventPublisher,
      FileRepository fileRepository) {
    this.defaultEncryption = defaultEncryption;
    this.secureEncryption = secureEncryption;
    this.codeGenerator = codeGenerator;
    this.eventPublisher = eventPublisher;
    this.fileRepository = fileRepository;
  }

  @Override
  public String processAnonymousUpload(String filePath, boolean useFastEncryption) {
    var code = codeGenerator.generate();
    var strategy = useFastEncryption ? defaultEncryption : secureEncryption;

    try {
      var rawContent = Files.readString(Paths.get(filePath));
      var encryptedData = strategy.encryptData(rawContent);
      fileRepository.storeSecurely(code, encryptedData);

      return code;

    } catch (IOException e) {
      System.out.println("Error: Could not read file at " + filePath + ". Please check the path.");
      return null;
    }
  }

  @Override
  public void simulateTimeExpiration(String code, String filePath) {
    System.out.println("Timer hit zero for " + filePath + "...");
    eventPublisher.publishEvent(new FileExpiredEvent(code, filePath));
  }
}
