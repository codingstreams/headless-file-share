package com.example.headless_file_sharing.cli;

import com.example.headless_file_sharing.service.download.DownloadService;
import com.example.headless_file_sharing.service.upload.UploadService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class TerminalInterfaceImpl implements TerminalInterface{
  private final Scanner scanner;
  private final UploadService uploadService;
  private final DownloadService downloadService;

  public TerminalInterfaceImpl(Scanner scanner, UploadService uploadService, DownloadService downloadService) {
    this.scanner = scanner;
    this.uploadService = uploadService;
    this.downloadService = downloadService;
  }

  @Override
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

    var code = uploadService.processAnonymousUpload(path, fastOpt);
    System.out.println("SUCCESS! Access Code: " + code);
  }

  private void handleDownload() {
    var code = prompt("Enter 6-character access code: ");
    var dest = prompt("Enter destination path (e.g., recovered.txt): ");
    boolean fastOpt = promptBoolean("Was this saved with fast encryption? (y/n): ");

    downloadService.processDownload(code, dest, fastOpt);
  }

  private void handleExpirationSimulation() {
    var code = prompt("Enter access code of expired file: ");
    var path = prompt("Enter the file path to verify deletion: ");

    uploadService.simulateTimeExpiration(code, path);
  }

  private String prompt(String message) {
    System.out.print(message);
    return scanner.nextLine();
  }

  private boolean promptBoolean(String message) {
    return prompt(message).equalsIgnoreCase("y");
  }
}
