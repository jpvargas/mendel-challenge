package com.mendel.transactions.common.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Entry point for requests for container health. */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class HealthCheckController {

  public static final String HEALTH_URL = "/health";

  @GetMapping(value = HEALTH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HttpStatus> healthCheck() {
    return ResponseEntity.ok().build();
  }
}
