package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.TokenHeaderGenerator;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.UriGenerator;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.Review;
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
public class ReviewsService {

 private final RestTemplate restTemplate;
 private final TokenHeaderGenerator tokenHeaderGenerator;
 private final UriGenerator uri;

 public List<Review> getReviewList() {
  ResponseEntity<Review[]> apiResponse = restTemplate.exchange(
          uri.getBooksUri(),
          HttpMethod.GET,
          tokenHeaderGenerator.getTokenHeader(),
          Review[].class
  );


  if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return Arrays.asList(apiResponse.getBody());
  } else {
   return Collections.emptyList();
  }
 }

 public Review getReviewByIsbn(String isbn) {
  ResponseEntity<Review> apiResponse = restTemplate.exchange(
          uri.getReviewsUri() + "/" + isbn,
          HttpMethod.GET,
          tokenHeaderGenerator.getTokenHeader(),
          Review.class
  );


  if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return apiResponse.getBody();
  } else {
   return apiResponse.getBody();
  }
 }

 public Review createReview(Review review, String isbn) {
  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();

  ResponseEntity<Review> apiResponse = restTemplate.exchange(
          uri.getReviewsUri()+"/" + isbn,
          HttpMethod.POST,
          new HttpEntity<>(review, headers),
          Review.class
  );


  if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return apiResponse.getBody();
  } else {
   return apiResponse.getBody();
  }
 }

 public void deleteReviewByIsbn(String isbn) {
  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();
  HttpEntity<String> entity = new HttpEntity<>(headers);

  restTemplate.exchange(
          uri.getReviewsUri() + "/" + isbn,
          HttpMethod.DELETE,
          entity,
          String.class
  );
 }

 public Review updateReview(Review review) {
  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();
  HttpEntity<Review> entity = new HttpEntity<>(review, headers);

  ResponseEntity<Review> apiResponse = restTemplate.exchange(
          uri.getReviewsUri() + "/" +  review.getBookIsbn(),
          HttpMethod.PUT,
          new HttpEntity<>(review, headers),
          Review.class
  );


  if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return apiResponse.getBody();
  } else {
   return apiResponse.getBody();
  }
 }
 }
