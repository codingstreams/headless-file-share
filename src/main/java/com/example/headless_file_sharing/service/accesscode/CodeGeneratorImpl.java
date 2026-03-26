package com.example.headless_file_sharing.service.accesscode;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class CodeGeneratorImpl implements CodeGenerator {
  private static final String CHARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
  private static final int CODE_LENGTH = 6;
  private final SecureRandom secureRandom;

  public CodeGeneratorImpl(SecureRandom secureRandom) {
    this.secureRandom = secureRandom;
  }

  @Override
  public String generate() {
    var code = new StringBuilder(CODE_LENGTH);

    for (int i = 0; i < CODE_LENGTH; i++) {
      var randomIndex = secureRandom.nextInt(CHARACTERS.length());
      code.append(CHARACTERS.charAt(randomIndex));
    }

    return code.toString();
  }
}
