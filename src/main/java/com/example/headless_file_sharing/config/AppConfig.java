package com.example.headless_file_sharing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class AppConfig {

  @Bean
  Scanner consoleScanner(){
    return new Scanner(System.in);
  }
}
