package com.example.headless_file_sharing.scheduler.filecleanup;

import com.example.headless_file_sharing.event.FileExpiredEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileCleanupScheduler {
  private static final Path STORAGE_DIR = Paths.get("secure_storage/");
  private final ApplicationEventPublisher eventPublisher;

  public FileCleanupScheduler(ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  public void simulateTimeExpiration(String code) {
    System.out.println("Timer hit zero for " + code + "...");
    eventPublisher.publishEvent(new FileExpiredEvent(code, null));
  }
}
