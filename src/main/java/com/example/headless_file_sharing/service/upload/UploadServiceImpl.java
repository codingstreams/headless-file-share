package com.example.headless_file_sharing.service.upload;

import com.example.headless_file_sharing.model.FileMetadata;
import com.example.headless_file_sharing.repo.FileRepo;
import com.example.headless_file_sharing.security.EncryptionStrategy;
import com.example.headless_file_sharing.service.accesscode.CodeGenerator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class UploadServiceImpl implements UploadService {
  private final CodeGenerator codeGenerator;
  private final EncryptionStrategy base64Enc;
  private final EncryptionStrategy aesEnc;
  private final FileRepo fileRepo;

  public UploadServiceImpl(CodeGenerator codeGenerator,
                           EncryptionStrategy base64Enc,
                           EncryptionStrategy aesEnc,
                           FileRepo fileRepo) {
    this.codeGenerator = codeGenerator;
    this.base64Enc = base64Enc;
    this.aesEnc = aesEnc;
    this.fileRepo = fileRepo;
  }

  @Override
  public String upload(String filePath,
                       boolean useFastEncryption) {
    var code = codeGenerator.generate();
    var encStrategy = useFastEncryption ? base64Enc : aesEnc;

    try {
      var rawContent = Files.readString(Path.of(filePath));
      var encData = encStrategy.encrypt(rawContent);

      fileRepo.store(code, encData, FileMetadata.FILE_EXP_TIME_IN_SEC);

      return code;
    } catch (IOException e) {
      System.out.println("Error: Could not read file at " + filePath + ". Please check the path.");
      return null;
    }
  }
}
