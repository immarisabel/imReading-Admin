package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.TokenHeaderGenerator;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.UriGenerator;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.Book;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.SearchBook;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SearchService {


 private final RestTemplate restTemplate;
 private final TokenHeaderGenerator tokenHeaderGenerator;
 private final UriGenerator uri;


 public List<SearchBook> getResultsForBooksSearch(String query) {
  ResponseEntity<SearchBook[]> apiResponse = restTemplate.exchange(
          uri.getSearchUri() + "?query=" + query,
          HttpMethod.GET,
          tokenHeaderGenerator.getTokenHeader(),
          SearchBook[].class
  );
  return List.of(Objects.requireNonNull(apiResponse.getBody()));
 }


}
