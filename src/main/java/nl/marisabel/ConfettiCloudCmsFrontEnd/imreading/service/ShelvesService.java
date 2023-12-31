package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.TokenHeaderGenerator;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.UriGenerator;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.BooksInShelves;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.Shelf;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class ShelvesService {

 private final RestTemplate restTemplate;
 private final TokenHeaderGenerator tokenHeaderGenerator;
 private final UriGenerator uri;

 public List<Shelf> getShelvesList() {

  ResponseEntity<Shelf[]> apiResponse = restTemplate.exchange(
          uri.getShelvesUri(),
          HttpMethod.GET,
          tokenHeaderGenerator.getTokenHeader(),
          Shelf[].class);


  if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return Arrays.asList(Objects.requireNonNull(apiResponse.getBody()));
  } else {
   return Collections.emptyList();
  }
 }




 public Shelf getShelfByName(String name) {

  ResponseEntity<Shelf> apiResponse = restTemplate.exchange(
          uri.getShelvesUri() + "/" + name,
          HttpMethod.GET,
          tokenHeaderGenerator.getTokenHeader(),
          Shelf.class);


  if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return apiResponse.getBody();
  } else {
   return null;
  }
 }

 public Shelf getShelfById(int id) {
  ResponseEntity<Shelf> apiResponse = restTemplate.exchange(
          uri.getShelvesUri() + "/shelf/" + id,
          HttpMethod.GET,
          tokenHeaderGenerator.getTokenHeader(),
          Shelf.class);


  if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return apiResponse.getBody();
  } else {
   return null;
  }

 }

 public Shelf createShelf(Shelf shelf) {
  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();

  log.info("🟢 shelf to create: " + shelf.getShelfName() + shelf.getId());

  HttpEntity<Shelf> entity = new HttpEntity<>(shelf, headers);

  log.info("🟢 entity: " + entity.getBody().toString());

  ResponseEntity<Shelf> apiResponse = restTemplate.exchange(
          uri.getShelvesUri(),
          HttpMethod.POST,
          entity,
          Shelf.class);

  return apiResponse.getStatusCode().is2xxSuccessful() ? apiResponse.getBody() : null;
 }

 public Shelf updateShelf(Shelf shelf) {
  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();

  ResponseEntity<Shelf> apiResponse = restTemplate.exchange(
          uri.getShelvesUri() + "/" + shelf.getId(),
          HttpMethod.PUT,
          new HttpEntity<>(shelf, headers),
          Shelf.class);


  if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return apiResponse.getBody();
  } else {
   return null;
  }
 }

 public void deleteShelf(String name) {
  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();
  HttpEntity<String> entity = new HttpEntity<>(headers);

  ResponseEntity<Void> apiResponse = restTemplate.exchange(
          uri.getShelvesUri() + "/" + name,
          HttpMethod.DELETE,
          entity,
          Void.class
  );

  if (!apiResponse.getStatusCode().is2xxSuccessful()) {
   throw new RuntimeException("Failed to delete shelf. HTTP Status: " + apiResponse.getStatusCodeValue());
  }
 }


}
