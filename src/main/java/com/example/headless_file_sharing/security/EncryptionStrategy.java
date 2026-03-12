package com.example.headless_file_sharing.security;

public interface EncryptionStrategy {
  String encryptData(String data);
  String decryptData(String encryptedData);
}
