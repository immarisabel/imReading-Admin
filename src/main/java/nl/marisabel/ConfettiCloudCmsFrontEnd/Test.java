package nl.marisabel.ConfettiCloudCmsFrontEnd;


import lombok.RequiredArgsConstructor;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.Book;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.ReadingData;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.ReadingStatus;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service.BooksService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class Test {

 private final BooksService booksService;

 @GetMapping("/test")
 @ResponseBody
 public List<Book> test() {

  List<Book> booksList = booksService.getBooksList();

  List<Book> filteredList = booksList.stream()
          .filter(book -> {
           ReadingData readingData = book.getReadingData();
           return readingData != null && ReadingStatus.READING.equals(readingData.getStatus());
          })
          .collect(Collectors.toList());

  return filteredList;


 }

}
