package com.josdem.web.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ActiveProfiles("test")
class MessageControllerTest {

  @BeforeAll
  void setup() {
    // Get access token with rest template
  }

  @Test
  @DisplayName("it gets client ID")
  void shouldGetClientId(TestInfo testInfo) throws Exception {
    log.info("Running: {}", testInfo.getDisplayName());
    // Get client ID with mockMvc
  }
}
