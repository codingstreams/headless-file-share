package com.example.headless_file_sharing.service.download;

import com.example.headless_file_sharing.repo.FileRepo;
import com.example.headless_file_sharing.security.EncryptionStrategy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class DownloadServiceImpl implements DownloadService {
  private final FileRepo fileRepo;
  private final EncryptionStrategy base64Enc;
  private final EncryptionStrategy aesEnc;

  public DownloadServiceImpl(FileRepo fileRepo,
                             EncryptionStrategy base64Enc,
                             EncryptionStrategy aesEnc) {
    this.fileRepo = fileRepo;
    this.base64Enc = base64Enc;
    this.aesEnc = aesEnc;
  }

  @Override
  public boolean download(String accessCode,
                          String destinationPath,
                          boolean isFastEncryption) {
    var encData = fileRepo.retrieve(accessCode);

    if (encData.isEmpty()) {
      System.out.println("Error: No file found for access code " + accessCode + ". It may have expired.");
      return false;
    }

    var encStrategy = isFastEncryption ? base64Enc : aesEnc;
    var data = encStrategy.decrypt(encData.get());

    try {
      Files.writeString(Paths.get(destinationPath), data);
      System.out.println("Success! File decrypted and saved to: " + destinationPath);
      return true;
    } catch (IOException e) {
      System.out.println("Error writing file to destination: " + destinationPath);
      return false;
    }
  }
}
