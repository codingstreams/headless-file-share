package com.example.headless_file_sharing.security;

public interface EncryptionStrategy {
  String encrypt(String rawContent);

  String decrypt(String encData);
}
