package com.josdem.web.resource;

import com.josdem.web.resource.model.AuthToken;
import com.josdem.web.resource.util.CredentialsEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.GRANT_TYPE;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.SCOPE;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class MessageControllerTest {

  public static final String TEST_USERNAME = "client";
  public static final String TEST_PASSWORD = "secret";
  public static final String WRITE = "write";
  public static final String URL = "https://auth.josdem.io/oauth2/token";

  private final RestTemplate restTemplate = new RestTemplate();
  private final HttpHeaders httpHeaders = new HttpHeaders();

  private HttpEntity<MultiValueMap> httpEntity;
  private ResponseEntity<AuthToken> response;
  private MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

  @BeforeAll
  void setup() {
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    httpHeaders.add(AUTHORIZATION, CredentialsEncoder.encode(TEST_USERNAME, TEST_PASSWORD));
    body.add(GRANT_TYPE, CLIENT_CREDENTIALS.getValue());
    body.add(SCOPE, WRITE);
    httpEntity = new HttpEntity<>(body, httpHeaders);
    response = restTemplate.postForEntity(URL, httpEntity, AuthToken.class);
  }

  @Test
  @DisplayName("it gets client ID")
  void shouldGetClientId(TestInfo testInfo) {
    log.info("Running: {}", testInfo.getDisplayName());
    assertNotNull(response.getBody().getAccessToken());
    // Get client ID with mockMvc
  }
}
