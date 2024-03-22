package com.josdem.web.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.GRANT_TYPE;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.SCOPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class MessageControllerTest {

  public static final String WRITE = "write";
  public static final String URL = "https://auth.josdem.io/oauth2/token";
  public static final String BEARER = "Bearer ";
  public static final String BASIC = "Basic ";

  @Value("${CLIENT}")
  private String client;

  @Value("${SECRET}")
  private String secret;

  private final RestTemplate restTemplate = new RestTemplate();
  private final HttpHeaders httpHeaders = new HttpHeaders();
  private final MockMvc mockMvc;

  private HttpEntity<MultiValueMap> httpEntity;
  private ResponseEntity<AuthToken> response;
  private MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

  @BeforeAll
  void setup() {
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    httpHeaders.add(AUTHORIZATION, BASIC + CredentialsEncoder.encode(client, secret));
    body.add(GRANT_TYPE, CLIENT_CREDENTIALS.getValue());
    body.add(SCOPE, WRITE);
    httpEntity = new HttpEntity<>(body, httpHeaders);
    response = restTemplate.postForEntity(URL, httpEntity, AuthToken.class);
  }

  @Test
  @DisplayName("it gets client ID")
  void shouldGetClientId(TestInfo testInfo) throws Exception {
    log.info("Running: {}", testInfo.getDisplayName());
    mockMvc
        .perform(get("/").header(AUTHORIZATION, BEARER + response.getBody().getAccessToken()))
        .andExpect(status().isOk())
        .andExpect(result -> assertNotNull(result.getResponse()));
  }

  @Test
  @DisplayName("it gets secret message")
  void shouldGetsSecretMessage(TestInfo testInfo) throws Exception {
    log.info("Running: {}", testInfo.getDisplayName());
    mockMvc
        .perform(
            get("/message").header(AUTHORIZATION, BEARER + response.getBody().getAccessToken()))
        .andExpect(status().isOk())
        .andExpect(
            result -> assertEquals("Secret message!", result.getResponse().getContentAsString()));
  }

  @Test
  @DisplayName("it post secret message")
  void shouldPostSecretMessage(TestInfo testInfo) throws Exception {
    log.info("Running: {}", testInfo.getDisplayName());
    mockMvc
        .perform(
            post("/message")
                .header(AUTHORIZATION, BEARER + response.getBody().getAccessToken())
                .content("Hello World!"))
        .andExpect(status().isOk())
        .andExpect(
            result ->
                assertEquals("Content: Hello World!", result.getResponse().getContentAsString()));
  }
}
