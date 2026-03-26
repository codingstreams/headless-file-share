package com.example.headless_file_sharing.event;

public record FileExpiredEvent(String code, String filePath) {
}
