package com.example.headless_file_sharing.service.upload;

public interface UploadService {
  String upload(String filePath,
                boolean useFastEncryption);
}
