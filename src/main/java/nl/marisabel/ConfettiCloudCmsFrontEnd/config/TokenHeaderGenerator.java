package nl.marisabel.ConfettiCloudCmsFrontEnd.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Log4j2
public class TokenHeaderGenerator {

 private final RestTemplate restTemplate;
 private final ApiTokenManager tokenManager;

 public HttpEntity<String> getTokenHeader() {
  String token = tokenManager.retrieveTokenFromCookieForTheControllers();
  HttpHeaders headers = new HttpHeaders();
  headers.set("Authorization", "Bearer " + token);
  return new HttpEntity<>(headers);
 }
}
