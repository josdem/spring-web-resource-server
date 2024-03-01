package com.josdem.web.resource.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageController {

  private String message = "Secret message!";

  @GetMapping("/")
  public String index(@AuthenticationPrincipal Jwt jwt) {
    return String.format("Hello, %s!", jwt.getSubject());
  }

  @GetMapping("/message")
  public String message() {
    return message;
  }

  @PostMapping("/message")
  public String createMessage(@RequestBody String message) {
    log.info("Message received: {}", message);
    this.message = message;
    return String.format("Content: %s", message);
  }
}
