package com.example.headless_file_sharing.listener;

import com.example.headless_file_sharing.events.FileExpiredEvent;
import com.example.headless_file_sharing.repository.FileRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FileCleanupListener {
  private final FileRepository fileRepository;

  public FileCleanupListener(FileRepository fileRepository) {
    this.fileRepository = fileRepository;
  }

  @EventListener
  public void handleFileExpiration(FileExpiredEvent event){
    System.out.println("\n[SYSTEM EVENT] Expiration triggered for code: " + event.accessCode());
    fileRepository.wipeFromStorage(event.accessCode());
  }
}
