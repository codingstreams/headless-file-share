package com.example.headless_file_sharing.events;

public record FileExpiredEvent(
    String accessCode,
    String filePath) {
}
