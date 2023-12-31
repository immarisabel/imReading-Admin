package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.TokenHeaderGenerator;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.UriGenerator;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.Logs;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
@Log4j2
public class LogsService {

 private final RestTemplate restTemplate;
 private final TokenHeaderGenerator tokenHeaderGenerator;
 private final UriGenerator uri;


 public List<Logs> getLogsForBookAsList(String isbn) {
  ResponseEntity<Logs[]> apiResponse = restTemplate.exchange(
          uri.getReadingLogsUri() + "/" + isbn,
          HttpMethod.GET,
          tokenHeaderGenerator.getTokenHeader(),
          Logs[].class
  );

  if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return Arrays.asList(apiResponse.getBody());
  } else {
   log.info("❌  response code: " + apiResponse.getStatusCode());
   return Collections.emptyList();
  }
 }

 public Logs addANewLog(Logs logs) {
  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();
  log.info("📝 🔵 URI: " + uri.getReadingLogsUri() + "/");

  try {
   ObjectMapper objectMapper = new ObjectMapper();
   String logsJson = objectMapper.writeValueAsString(logs);

   log.info("📝 🔵 adding log to Logs Object: " + logsJson);
   log.info("📝 🟢 adding log to ISBN: " + logs.getIsbn());

   ResponseEntity<Logs> apiResponse = restTemplate.exchange(
           uri.getReadingLogsUri() + "/",
           HttpMethod.POST,
           new HttpEntity<>(logs, headers),
           Logs.class
   );

   if (apiResponse.getStatusCode().is2xxSuccessful()) {
    return apiResponse.getBody();
   } else {
    return null;
   }
  } catch (JsonProcessingException e) {
   log.error("Error converting Logs to JSON: " + e.getMessage());
   return null;
  }
 }


 public  Logs updateLog(Logs logs, Long id) {
  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();
  HttpEntity<Logs> entity = new HttpEntity<>(logs, headers);
  ResponseEntity<Logs> apiResponse = restTemplate.exchange(
          uri.getReadingLogsUri() + "/" + id,
          HttpMethod.PUT,
          entity,
          Logs.class
  );
  return apiResponse.getBody();

 }




 public Logs getLogById(Logs logs, Long id) {
  ResponseEntity<Logs> apiResponse = restTemplate.exchange(
          uri.getReadingLogsUri() + "/log/" + id,
          HttpMethod.GET,
          tokenHeaderGenerator.getTokenHeader(),
          Logs.class
  );
  return apiResponse.getBody();
 }

 public void deleteAllLogsForABook(String isbn) {
  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();
  HttpEntity<String> entity = new HttpEntity<>(headers);

  restTemplate.exchange(
          uri.getReadingLogsUri() + "/" + isbn,
          HttpMethod.DELETE,
          entity,
          String.class
  );
 }

 public void deleteLogById(Long id) {

  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();
  HttpEntity<String> entity = new HttpEntity<>(headers);

  restTemplate.exchange(
          uri.getReadingLogsUri() + "/" + id,
          HttpMethod.DELETE,
          entity,
          Void.class
  );

 }





}
