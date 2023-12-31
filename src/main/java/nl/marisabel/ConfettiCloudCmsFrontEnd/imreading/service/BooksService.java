package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.TokenHeaderGenerator;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.UriGenerator;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class BooksService {

 private final RestTemplate restTemplate;
 private final TokenHeaderGenerator tokenHeaderGenerator;
 private final UriGenerator uri;


 public Book populateBookData(String isbn, String title, String author, int pages, String thumbnailUrl, String selfLink) {
  Book book = new Book();
  book.setIsbn(isbn);
  book.setTitle(title);
  book.setAuthor(author);
  book.setPages(pages);
  book.setThumbnailUrl(thumbnailUrl);
  book.setSelfLink(selfLink);
  return book;
 }

 public Map<String, Long> getBooksListWithBookCount() {
  ResponseEntity<BooksInShelves[]> apiResponse = restTemplate.exchange(
          uri.getBooksUri(),
          HttpMethod.GET,
          tokenHeaderGenerator.getTokenHeader(),
          BooksInShelves[].class
  );

  Map<String, Long> shelfBookCountMap = Arrays.stream(apiResponse.getBody())
          .flatMap(book -> book.getShelves().stream().map(shelfId -> Map.entry(shelfId, book.getIsbn())))
          .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.counting()));

  return shelfBookCountMap;
 }

 public Book getBookByIsbn(String isbn) {
  ResponseEntity<Book> apiResponse = restTemplate.exchange(
          uri.getBooksUri() + "/" + isbn,
          HttpMethod.GET,
          tokenHeaderGenerator.getTokenHeader(),
          Book.class
  );


  if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return apiResponse.getBody();
  } else {
   return new Book();
  }
 }

 public List<Book> getBooksList() {
  try {
   ResponseEntity<Book[]> apiResponse = restTemplate.exchange(
           uri.getBooksUri(),
           HttpMethod.GET,
           tokenHeaderGenerator.getTokenHeader(),
           Book[].class
   );

   if (apiResponse.getStatusCode().is2xxSuccessful()) {
    return Arrays.asList(Objects.requireNonNull(apiResponse.getBody()));
   } else {
    return Collections.emptyList();
   }
  } catch (Exception e) {
   return Collections.emptyList();
  }
 }

 public List<Book> getBooksByShelfId(String shelfId) {
  ResponseEntity<Book[]> apiResponse = restTemplate.exchange(
          uri.getBooksUri(),
          HttpMethod.GET,
          tokenHeaderGenerator.getTokenHeader(),
          Book[].class
  );

  return Arrays.stream(Objects.requireNonNull(apiResponse.getBody()))
          .filter(book -> book.getShelves().contains(shelfId))
          .collect(Collectors.toList());
 }

 public List<Book> getBooksListedByFinishedDate() {
  List<Book> booksList = getBooksList();
  // Sort the booksList using streams
  booksList = booksList.stream()
          .sorted(Comparator.comparing(
                  Book::getReadingData,
                  Comparator.nullsLast(
                          Comparator.comparing(
                                  ReadingData::getFinishedDate,
                                  Comparator.nullsLast(Comparator.reverseOrder())
                          )
                  )
          ))
          .collect(Collectors.toList());

  return booksList;
 }

 public List<Book> getBooksByReadingStatus(String status) {
  List<Book> booksList = getBooksList();
  // Convert the status String to uppercase for case-insensitive comparison
  String uppercaseStatus = status.toUpperCase();

  List<Book> filteredList = booksList.stream()
          .filter(book -> {
           ReadingData readingData = book.getReadingData();
           return readingData != null && ReadingStatus.valueOf(uppercaseStatus).equals(readingData.getStatus());
          })
          .collect(Collectors.toList());
  return filteredList;

 }

 // CRUD
 public Book createBook(Book book) {
  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();
  log.info("📕 book to add: " + book.getTitle() + " " + book.getIsbn() + book.getShelves());
  ResponseEntity<Book> apiResponse = restTemplate.exchange(
          uri.getBooksUri(),
          HttpMethod.POST,
          new HttpEntity<>(book, headers),
          Book.class
  );


  return apiResponse.getStatusCode().is2xxSuccessful() ? apiResponse.getBody() : null;

 }

 public Book updateBook(Book book) {
  log.info("📕 book to update: " + book.getIsbn());

  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();

  ResponseEntity<Book> apiResponse = restTemplate.exchange(
          uri.getBooksUri() + "/" + book.getIsbn(),
          HttpMethod.PUT,
          new HttpEntity<>(book, headers),
          Book.class
  );

  log.info("📘 book updating: " + book.getIsbn());


  if (apiResponse.getStatusCode().is2xxSuccessful()) {
   return apiResponse.getBody();
  } else {
   return null;
  }
 }

 public void deleteBook(String isbn) {
  HttpHeaders headers = tokenHeaderGenerator.getTokenHeader().getHeaders();
  HttpEntity<String> entity = new HttpEntity<>(headers);

  restTemplate.exchange(
          uri.getBooksUri() + "/" + isbn,
          HttpMethod.DELETE,
          entity,
          String.class
  );
 }


}