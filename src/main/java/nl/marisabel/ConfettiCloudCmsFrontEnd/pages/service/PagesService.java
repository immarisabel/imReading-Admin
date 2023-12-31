package nl.marisabel.ConfettiCloudCmsFrontEnd.pages.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.TokenHeaderGenerator;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.UriGenerator;
import nl.marisabel.ConfettiCloudCmsFrontEnd.pages.dto.Page;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class PagesService {

 private final RestTemplate restTemplate;
 private final TokenHeaderGenerator tokenHeaderGenerator;
 private final UriGenerator uri;


 public Page getPageById(String id) {


  ResponseEntity<Page> apiResponse = restTemplate.exchange(
          uri.getPagesUri() + "/" + id,
          HttpMethod.GET,
          tokenHeaderGenerator.getTokenHeader(),
          Page.class);

  log.info("🟢 response code: " + apiResponse.getStatusCode());

  if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return apiResponse.getBody();
  } else {
   return null;
  }
 }


 public List<Page> getPagesList() {


  ResponseEntity<Page[]> apiResponse = restTemplate.exchange(
          uri.getPagesUri(),
          HttpMethod.GET,
          tokenHeaderGenerator.getTokenHeader(),
          Page[].class);

  log.info("🟢 response code: " + apiResponse.getStatusCode());

  if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return Arrays.asList(apiResponse.getBody());
  } else {
   return Collections.emptyList();
  }
 }

 public Page createPage(Page page) {

  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();

  log.info("🔵 headers: " + headers);

  ResponseEntity<Page> apiResponse = restTemplate.exchange(
          uri.getPagesUri(),
          HttpMethod.POST,
          new HttpEntity<>(page, headers),
          Page.class);

  log.info("🟢 response code: " + apiResponse.getStatusCode());

  if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return apiResponse.getBody();
  } else {
   return null;
  }
 }



 public Page updatePage(Page page) {
  log.info("🔵 page to update: " + page.getId());

  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();

  log.info("🔵 headers: " + headers);

  ResponseEntity<Page> apiResponse = restTemplate.exchange(
          uri.getPagesUri() + "/" + page.getId(),
          HttpMethod.PUT,
          new HttpEntity<>(page, headers),
          Page.class);

  log.info("🔵 page updating: " + page.getId());


  log.info("🟢 response code: " + apiResponse.getStatusCode());

  if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return apiResponse.getBody();
  } else {
   return null;
  }
 }




 public void deletePage(String id) {

  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();
  HttpEntity<String> entity = new HttpEntity<>(headers);

  restTemplate.exchange(
          uri.getPagesUri() + "/" + id,
          HttpMethod.DELETE,
          entity,
          String.class
  );
 }

}
