package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.TokenHeaderGenerator;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.UriGenerator;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.ReadingData; // Updated import
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;



@Service
@RequiredArgsConstructor
@Log4j2
public class ReadingDataService {

 private final RestTemplate restTemplate;
 private final TokenHeaderGenerator tokenHeaderGenerator;
 private final UriGenerator uri;

 public ReadingData getReadingDataByIsbn(String isbn) {
  log.info("💾 🔵 checking for reading data...");
  try {
   ResponseEntity<ReadingData> apiResponse = restTemplate.exchange(
           uri.getReadingDataUri() + "/" + isbn,
           HttpMethod.GET,
           tokenHeaderGenerator.getTokenHeader(),
           ReadingData.class
   );

   if (apiResponse.getStatusCode().is2xxSuccessful()) {
    return apiResponse.getBody();

   } else if (apiResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
    log.info(".💾 🔴 No data found for ISBN: " + isbn);
    return new ReadingData();
   } else {
    log.error("💾 ⛔ Error retrieving reading data for ISBN: " + isbn);
    return new ReadingData();
   }
  } catch (RestClientException e) {
   log.error("💾 ⛔ Error communicating with the server: " + e.getMessage());
   return new ReadingData();
  }
 }




 public ReadingData createReadingData(ReadingData readingData) {
  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();

  ResponseEntity<ReadingData> apiResponse = restTemplate.exchange(
          uri.getReadingDataUri()+"/",
          HttpMethod.POST,
          new HttpEntity<>(readingData, headers),
          ReadingData.class
  );


  if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return apiResponse.getBody();
  } else {
   return null;
  }
 }

 public void deleteReadingData(String isbn) {
  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();
  HttpEntity<String> entity = new HttpEntity<>(headers);

  restTemplate.exchange(
          uri.getReadingDataUri() + "/" + isbn,
          HttpMethod.DELETE,
          entity,
          String.class
  );
 }

}
