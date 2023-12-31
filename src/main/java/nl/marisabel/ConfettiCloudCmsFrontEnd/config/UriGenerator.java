package nl.marisabel.ConfettiCloudCmsFrontEnd.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class UriGenerator {
 private final ApiProperties properties;

 public String getShelvesUri() {
  return properties.getApiUrlRoot() + properties.getBooksUrl() + "/shelves";
 }

 public String getBooksUri() {
  return properties.getApiUrlRoot() + properties.getBooksUrl() + "/books";
 }

 public String getReadingLogsUri() {
  return properties.getApiUrlRoot() + properties.getBooksUrl() + "/logs";
 }

 public String getReadingDataUri() {
  return properties.getApiUrlRoot() + properties.getBooksUrl() + "/reading";
 }

 public String getSearchUri() {
  return properties.getApiUrlRoot() + properties.getBooksUrl() + "/search";
 }

 public String getLinksUri() {
  return properties.getApiUrlRoot() + properties.getMenuUrl();
 }

 public String getPagesUri() {
  return properties.getApiUrlRoot() + properties.getPagesUrl();
 }

 public String getReviewsUri() {  return properties.getApiUrlRoot() + properties.getBooksUrl() + "/reviews"; }
}

