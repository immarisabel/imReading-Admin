package nl.marisabel.ConfettiCloudCmsFrontEnd.links.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.TokenHeaderGenerator;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.UriGenerator;
import nl.marisabel.ConfettiCloudCmsFrontEnd.links.dto.Link;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class LinkService {

 private final RestTemplate restTemplate;
 private final TokenHeaderGenerator tokenHeaderGenerator;
 private final UriGenerator uri;
 private final HttpServletResponse response;
 private final HttpServletRequest request;


 public Link getLinkById(String id) {


  ResponseEntity<Link> apiResponse = restTemplate.exchange(
          uri.getLinksUri() + "/" + id,
          HttpMethod.GET,
          tokenHeaderGenerator.getTokenHeader(),
          Link.class);


  if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return apiResponse.getBody();
  } else {
   return null;
  }
 }


 public List<Link> getLinkList() {
  ResponseEntity<Link[]> apiResponse = restTemplate.exchange(
          uri.getLinksUri() + "/all",
          HttpMethod.GET,
          tokenHeaderGenerator.getTokenHeader(),
          Link[].class
  );


  if (apiResponse.getStatusCode() == HttpStatus.NO_CONTENT) {
   return Collections.emptyList(); // Return an empty list for 204 (NO_CONTENT) response.
  } else if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return Arrays.asList(apiResponse.getBody());
  } else {
   return Collections.emptyList(); // Handle other error cases by returning an empty list.
  }
 }


 public List<Link> getActiveLinks() {
  ResponseEntity<Link[]> apiResponse = restTemplate.exchange(
          uri.getLinksUri() + "/active",
          HttpMethod.GET,
          tokenHeaderGenerator.getTokenHeader(),
          Link[].class
  );

  if (apiResponse.getStatusCode() == HttpStatus.NO_CONTENT) {
   return Collections.emptyList(); // Return an empty list for 204 (NO_CONTENT) response.
  } else if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return Arrays.asList(Objects.requireNonNull(apiResponse.getBody()));
  } else {
   return Collections.emptyList(); // Handle other error cases by returning an empty list.
  }
 }


 public void addLink(Link link) {

  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();

  log.info("🔵 headers: " + headers);
  log.info("🔵 link: " + link);

  ResponseEntity<Link> apiResponse = restTemplate.exchange(
          uri.getLinksUri(),
          HttpMethod.POST,
          new HttpEntity<>(link, headers),
          Link.class);


  if (apiResponse.getStatusCode().is2xxSuccessful()) {
   apiResponse.getBody();
  } else {
   apiResponse.getBody();
  }
 }


 public Link updateLink(Link link) {
  log.info("🔵 page to update: " + link.getId());

  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();

  log.info("🔵 headers: " + headers);

  ResponseEntity<Link> apiResponse = restTemplate.exchange(
          uri.getLinksUri() + "/" + link.getId(),
          HttpMethod.PUT,
          new HttpEntity<>(link, headers),
          Link.class);

  log.info("🔵 page updating: " + link.getId());


  if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return apiResponse.getBody();
  } else {
   return null;
  }
 }


public void deleteLink(String id) {
  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();
  HttpEntity<String> entity = new HttpEntity<>(headers);

  try {
      restTemplate.exchange(
              uri.getLinksUri() + "/" + id,
              HttpMethod.DELETE,
              entity,
              Void.class
      );
      // Deletion successful
      System.out.println("Link with ID: " + id + " deleted successfully.");
  } catch (HttpClientErrorException.NotFound notFoundException) {
      // Handle 404 error - Link not found
      System.out.println("Link with ID: " + id + " not found.");
  } catch (Exception e) {
      // Handle other exceptions
      System.err.println("Error deleting link with ID: " + id + ". " + e.getMessage());
  }
}



}
