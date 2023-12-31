package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.Book;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.ReadingData;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.Shelf;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin/book")
@RequiredArgsConstructor
@Log4j2
public class AdminBookController {

 private final BooksService books;
 private final ShelvesService shelves;
 private final ReadingDataService readingData;
 private final ReviewsService review;


 /** Get all books for list view in order of last finished date */
@GetMapping("/all")
public String getAllBooks(Model model) {
    List<Book> booksList = books.getBooksListedByFinishedDate();
    model.addAttribute("booksList", booksList);
    return "front/book-list";
}


 @PostMapping("/add")
 public String createBook(@ModelAttribute Book book) {
  log.info("Book to add: " + book.getTitle() + " " + book.getIsbn() + book.getShelves());
  Book createdBook = books.createBook(book);
  if (createdBook != null && createdBook.getIsbn() != null) {
   return "redirect:/public/book/" + createdBook.getIsbn();
  } else {
   return "/auth/book-creation-failed";
  }
 }

 @GetMapping("/add")
 public String loadCreateBookForm(Model model) {
 log.info("adding new book...");
  List<Shelf> shelvesList = shelves.getShelvesList();
  model.addAttribute("shelves", shelvesList);

  Book book = new Book();
  model.addAttribute("book", book);

  return "/auth/book/add-book";
 }

/** add book form from teh search results */
 @GetMapping("/add-result")
 public String loadCreateBookForm(
         @RequestParam String isbn,
         @RequestParam String title,
         @RequestParam String author,
         @RequestParam int pages,
         @RequestParam String thumbnailUrl,
         @RequestParam String selfLink,
         Model model) {

  List<Shelf> shelf = shelves.getShelvesList();
  model.addAttribute("shelves", shelf);

  Book book = books.populateBookData(isbn, title, author, pages, thumbnailUrl, selfLink);
  model.addAttribute("book", book);

  return "/auth/book/add-book";
 }


 @GetMapping("/update/{isbn}")
 public String loadBookToUpdate(@PathVariable("isbn") String isbn, Book book, Model model) {

  book = books.getBookByIsbn(isbn);
  model.addAttribute("book", book);

  List<Shelf> shelvesList = shelves.getShelvesList();
  model.addAttribute("shelves", shelvesList);

  return "/auth/book/update-book";
 }

 @PostMapping("/update/{isbn}")
 public String updateBook(@PathVariable("isbn") String isbn, Book book, Model model) {
  Book updatedBook = books.updateBook(book); // Updated method name
  model.addAttribute("book", updatedBook);

  if (updatedBook != null && updatedBook.getIsbn() != null) { // Updated property name
   String redirectUrl = "/public/book/" + updatedBook.getIsbn(); // Updated property name
   return "redirect:" + redirectUrl;
  } else {
   return "/auth/book/book-update-failed"; // Updated view name
  }
 }

 @PostMapping("/delete/{isbn}")
 public String deleteBook(@PathVariable("isbn") String isbn) {
  books.deleteBook(isbn);
  return "redirect:../list";
 }


}
