package nl.marisabel.ConfettiCloudCmsFrontEnd.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.ApiProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class ApiTokenManager {

 private final RestTemplate restTemplate;
 private final ApiProperties apiProperties;
 private final HttpServletRequest request;
 private final HttpServletResponse response;

 private String COOKIE_NAME = "confetti_token";

 public String retrieveTokenFromCookieForTheControllers() {
  Cookie tokenCookie = retrieveTokenFromCookie();
  if (tokenCookie == null) {
   String token = getAccessToken();
   storeCookie(token);
   return token;
  }
  tokenCookie = generateAccessToken();
  return tokenCookie.getValue();
 }

 public String getAccessToken() {

  String loginUrl = apiProperties.getApiUrlRoot() + "/secret-passage/v1/login";
  HttpHeaders headers = new HttpHeaders();
  headers.setContentType(MediaType.APPLICATION_JSON);

  Map<String, String> request = new HashMap<>();
  request.put("username", apiProperties.getUsername());
  request.put("password", apiProperties.getPassword());

  HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(request, headers);
  ResponseEntity<Map> response = restTemplate.exchange(loginUrl, HttpMethod.POST, requestEntity, Map.class);
  String token = (String) Objects.requireNonNull(response.getBody()).get("token");

  return token;
 }

 private void storeCookie(String token) {
  Cookie cookie = new Cookie(COOKIE_NAME, token);
  cookie.setPath("/");
  cookie.setHttpOnly(true);
  cookie.setMaxAge(160 * 60); // 160 minutes

  response.addCookie(cookie);
 }

 public Cookie retrieveTokenFromCookie() {
  Cookie[] cookies = request.getCookies();
  String token = null;

  if (cookies != null) {
   for (Cookie cookie : cookies) {
    if (COOKIE_NAME.equals(cookie.getName())) {
     token = cookie.getValue();
     break;
    }
   }
  }

  if (token == null) {
   return null;
  }

  return new Cookie(COOKIE_NAME, token);
 }

 private boolean isTokenValid(Cookie cookie) {
  Date tokenCreationTime = new Date(cookie.getMaxAge() * 1000L);
  Date currentTime = new Date();
  long timeElapsed = currentTime.getTime() - tokenCreationTime.getTime();
  if (timeElapsed >= 160 * 60 * 1000) {
   return true;
  }
  return false;
 }

 public Cookie generateAccessToken() {
  String token = getAccessToken();
  Cookie existingTokenCookie = retrieveTokenFromCookie();
  if (existingTokenCookie != null && isTokenValid(existingTokenCookie)) {
   return existingTokenCookie;
  } else {
   token = getAccessToken();
   storeCookie(token);
   return existingTokenCookie;
  }
 }

}
