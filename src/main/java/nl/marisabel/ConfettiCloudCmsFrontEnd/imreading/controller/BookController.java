package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.Book;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.Logs;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.ReadingData;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.Shelf;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service.BooksService;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service.ReadingDataService;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service.ShelvesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("public/book")
@RequiredArgsConstructor
@Log4j2
public class BookController {

 private final ReadingDataService readingDataService;
 private final ShelvesService shelves;
 private final BooksService bookLists;

 @GetMapping("/list")
 @ResponseBody
 public List<Book> getBooksList(Model model, Book book) {
  List<Book> booksList = bookLists.getBooksList();
  return booksList;
 }

 @GetMapping("/all")
 public String getAllBooks(Model model) {
  List<Book> booksList = bookLists.getBooksList();
  model.addAttribute("booksList", booksList);
  return "front/shelved-books";
 }

 @GetMapping("/{isbn}")
 public String getBookByIsbn(@PathVariable("isbn") String isbn, Book book, Model model) {
  Book bookData = bookLists.getBookByIsbn(isbn);
  log.info("📖 Opening book " + bookData.getTitle());
  model.addAttribute("book", bookData);

  if (bookData.getShelves() != null) {
   List<Shelf> shelvesList = new ArrayList<>();
   for (int i = 0; i < bookData.getShelves().size(); i++) {
    shelvesList.add(shelves.getShelfById(Integer.parseInt(bookData.getShelves().get(i))));
   }

   log.info("📚 shelves list: " + shelvesList.toString());
   model.addAttribute("shelves", shelvesList);

  } else {
   model.addAttribute("shelves", Collections.emptyList());
  }


  ReadingData readingData = readingDataService.getReadingDataByIsbn(isbn);
  if (readingData != null) {
   readingData.setBookIsbn(isbn);
   model.addAttribute("readingData", readingData);
   log.info("👌 🟢 data retrieved... " + readingData.getBookIsbn() + " " +
           readingData.getStartedDate() + " " +
           readingData.getFinishedDate() + " " +
           readingData.getCurrentPage());
  } else {
   model.addAttribute("readingData", new ReadingData());
   log.info("❌ 🔴 no data retrieved for " + isbn + ", creating new Object of ReadingData()");
  }

  model.addAttribute("readingLog", new Logs());
  return "front/book-details";
 }


 @GetMapping("/count-shelved-books")
 @ResponseBody
 public Map<String, Long> getCountOfBooksPerShelf(Model model) {
  return bookLists.getBooksListWithBookCount();
 }

 @GetMapping("/shelf/{shelfId}")
 public String getBooksByShelfId(@PathVariable String shelfId, Model model) {
  List<Book> booksList = bookLists.getBooksByShelfId(shelfId);

  model.addAttribute("booksList", booksList);
  model.addAttribute("shelfId", shelfId);

  return "front/shelved-books";
 }


 @GetMapping("/status/{status}")
 public String getBookByStatus(@PathVariable String status, Model model) {
  List<Book> booksList = bookLists.getBooksByReadingStatus(status);
  model.addAttribute("booksList", booksList);
  return "front/shelved-books";
 }


}



















