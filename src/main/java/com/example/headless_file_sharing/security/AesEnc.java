package com.example.headless_file_sharing.security;

import org.springframework.stereotype.Component;

@Component
public class AesEnc implements EncryptionStrategy {
  @Override
  public String encrypt(String rawContent) {
    return rawContent;
  }

  @Override
  public String decrypt(String encData) {
    return encData;
  }
}
