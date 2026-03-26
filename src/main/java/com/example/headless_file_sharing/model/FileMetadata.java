package com.example.headless_file_sharing.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public record FileMetadata(String code, int fileExpTimeInSec, Instant createdTime) {
  public static final int FILE_EXP_TIME_IN_SEC = 60;

  boolean isFileExpired() {
    return Instant.now()
        .isAfter(createdTime.plus(fileExpTimeInSec, ChronoUnit.SECONDS));
  }
}
