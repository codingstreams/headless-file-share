package com.example.headless_file_sharing.listener;

import com.example.headless_file_sharing.event.FileExpiredEvent;
import com.example.headless_file_sharing.model.FileMetadata;
import com.example.headless_file_sharing.repo.FileRepo;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FileCleanupListener {

  private final FileRepo fileRepo;

  public FileCleanupListener(FileRepo fileRepo) {
    this.fileRepo = fileRepo;
  }

  @EventListener
  public void handleFileExpiration(FileExpiredEvent event) {
    System.out.println("\n[SYSTEM EVENT] Expiration triggered for File: " + event.filePath());
    fileRepo.wipeFromStorage(new FileMetadata(event.code(), -1, null));
  }
}
