package com.example.headless_file_sharing.service.upload;

public interface UploadService {

  String processAnonymousUpload(String filePath, boolean useFastEncryption);
  void simulateTimeExpiration(String code, String filePath);
}
