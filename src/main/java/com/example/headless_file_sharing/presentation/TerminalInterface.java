package com.example.headless_file_sharing.presentation;

import com.example.headless_file_sharing.scheduler.filecleanup.FileCleanupScheduler;
import com.example.headless_file_sharing.service.download.DownloadService;
import com.example.headless_file_sharing.service.upload.UploadService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class TerminalInterface {
  private final Scanner scanner;
  private final UploadService uploadService;
  private final DownloadService downloadService;
  private final FileCleanupScheduler fileCleanupScheduler;

  public TerminalInterface(Scanner scanner,
                           UploadService uploadService,
                           DownloadService downloadService,
                           FileCleanupScheduler fileCleanupScheduler) {
    this.scanner = scanner;
    this.uploadService = uploadService;
    this.downloadService = downloadService;
    this.fileCleanupScheduler = fileCleanupScheduler;
  }

  public void start() {
    System.out.println("=== Headless Secure File Engine Started ===");
    var running = true;

    while (running) {
      displayMenu();
      String choice = scanner.nextLine();

      switch (choice) {
        case "1" -> handleUpload();
        case "2" -> handleDownload();
        case "3" -> handleExpirationSimulation();
        case "4" -> {
          System.out.println("Shutting down engine...");
          running = false;
        }
        default -> System.out.println("Invalid option. Please try again.");
      }
    }
  }

  private void displayMenu() {
    System.out.println("\nOptions: [1] Upload  [2] Download  [3] Simulate Expiration  [4] Exit");
    System.out.print("Enter choice: ");
  }

  private void handleUpload() {
    var path = prompt("Enter file path (e.g., /docs/secret.txt): ");
    var fastOpt = promptBoolean("Use fast, lower-security encryption? (y/n): ");

    var code = uploadService.upload(path, fastOpt);
    System.out.println("SUCCESS! Access Code: " + code);
  }


  private void handleDownload() {
    var code = prompt("Enter 6-character access code: ");
    var dest = prompt("Enter destination path (e.g., recovered.txt): ");
    boolean fastOpt = promptBoolean("Was this saved with fast encryption? (y/n): ");

    downloadService.download(code, dest, fastOpt);
  }

  private void handleExpirationSimulation() {
    fileCleanupScheduler.simulateTimeExpiration("84LZA3");
  }

  private String prompt(String message) {
    System.out.print(message);
    return scanner.nextLine();
  }

  private boolean promptBoolean(String message) {
    return prompt(message).equalsIgnoreCase("y");
  }
}
