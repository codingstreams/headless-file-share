package com.example.headless_file_sharing.security;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Base64Enc implements EncryptionStrategy {
  @Override
  public String encrypt(String rawContent) {
    return Base64.getEncoder()
        .encodeToString(rawContent.getBytes());
  }

  @Override
  public String decrypt(String encData) {
    return new String(Base64.getDecoder()
        .decode(encData.getBytes()));
  }
}
