package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.ReadingData;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service.ReadingDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/reading-data")
@Log4j2
public class AdminReadingDataController {

 private final ReadingDataService readingDataService;



 @GetMapping("/add/{isbn}")
 public String loadAddReadingDataForm(@PathVariable("isbn") String isbn, Model model) {
  log.info("🔵 Loading form for : " + isbn);
  ReadingData data = new ReadingData();
  data.setBookIsbn(isbn);
  log.info("🔵 Data: " + data.getBookIsbn());
  model.addAttribute("readingData", data);
  return "/auth/book/reading-data/add-reading-data";
 }

 @PostMapping("/add")
 public String addReadingData(@ModelAttribute ReadingData readingData) {
  log.info("🔵 Adding reading data for ISBN: " + readingData.getBookIsbn());
  ReadingData data = readingDataService.createReadingData(readingData);
  if (data != null && data.getBookIsbn() != null) {
   log.info("🟢 Added reading data for ISBN: " + data.getBookIsbn());
   return "redirect:/public/book/" + data.getBookIsbn();
  } else {
   return "error";
  }
 }

 // TODO simplify and untangle to service
 @GetMapping("/update/{isbn}")
 public String loadUpdateReadingDataForm(@PathVariable("isbn") String isbn, Model model) {
  log.info("🔵 Loading form for : " + isbn);

  ReadingData existingData = readingDataService.getReadingDataByIsbn(isbn);

  if (existingData == null) {
   model.addAttribute("message", "No data found for the given ISBN");
   return "front/message";
  }

  ReadingData updateData = new ReadingData();
  updateData.setBookIsbn(existingData.getBookIsbn());
  updateData.setStartedDate(formatForInputField(existingData.getStartedDate()));
  updateData.setFinishedDate(formatForInputField(existingData.getFinishedDate()));
  updateData.setStatus(existingData.getStatus());
  updateData.setCurrentPage(existingData.getCurrentPage());
  updateData.setRating(existingData.getRating());
  updateData.setFavorite(existingData.isFavorite());

  model.addAttribute("readingData", updateData);

  return "/auth/book/reading-data/add-reading-data";
 }

 private String formatForInputField(String date) {
  SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
  try {
   Date parsedDate = inputFormat.parse(date);
   return inputFormat.format(parsedDate);
  } catch (ParseException e) {
   e.printStackTrace();
   return date;
  }
 }


}
