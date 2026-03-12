package com.example.headless_file_sharing.service.download;

public interface DownloadService {
  boolean processDownload(String accessCode, String destinationPath, boolean isFastEncryption);
}
