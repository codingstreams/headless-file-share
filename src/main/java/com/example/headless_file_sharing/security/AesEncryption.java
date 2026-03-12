package com.example.headless_file_sharing.security;

import org.springframework.stereotype.Component;

@Component("aesEncryption")
public class AesEncryption implements EncryptionStrategy{
  @Override
  public String encryptData(String data) {
    return data;
  }

  @Override
  public String decryptData(String encryptedData) {
    return encryptedData;
  }
}
