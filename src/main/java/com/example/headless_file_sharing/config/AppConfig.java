package com.example.headless_file_sharing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;
import java.util.Scanner;

@Configuration
public class AppConfig {
  @Bean
  Scanner scanner() {
    return new Scanner(System.in);
  }

  @Bean
  SecureRandom secureRandom() {
    return new SecureRandom();
  }
}
