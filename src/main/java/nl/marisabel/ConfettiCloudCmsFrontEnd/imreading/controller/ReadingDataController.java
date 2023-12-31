package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.ReadingData;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service.ReadingDataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/public/reading-data")
@Log4j2
public class ReadingDataController {

 private final ReadingDataService readingDataService;


 @GetMapping("/{isbn}")
 @ResponseBody
 public ReadingData getReadingDataByIsbn(@PathVariable("isbn") String isbn, ReadingData readingData) {
  if (readingData != null) {
   log.info(readingData + " ... is NULL, creating empty object.");
   new ReadingData();
   readingData.setBookIsbn(isbn);
   return readingData;
  }
  return readingDataService.getReadingDataByIsbn(isbn);
 }
}

