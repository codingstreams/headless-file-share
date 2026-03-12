package com.example.headless_file_sharing.security;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component("base64Encryption")
@Primary
public class StandardBase64Encryption implements EncryptionStrategy {
  @Override
  public String encryptData(String data) {
    return Base64.getEncoder().encodeToString(data.getBytes());
  }

  @Override
  public String decryptData(String encryptedData) {
    return new String(Base64.getDecoder().decode(encryptedData.getBytes()));
  }
}
