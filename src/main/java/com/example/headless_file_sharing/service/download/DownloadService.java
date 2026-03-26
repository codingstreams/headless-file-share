package com.example.headless_file_sharing.service.download;

public interface DownloadService {
  boolean download(String accessCode,
                   String destinationPath,
                   boolean isFastEncryption);
}
