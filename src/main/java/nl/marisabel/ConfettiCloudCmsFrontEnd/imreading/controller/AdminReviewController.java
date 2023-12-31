package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.Book;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.Review;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service.BooksService;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service.ReviewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/review")
@Log4j2
public class AdminReviewController {

 private final ReviewsService reviewsService;
 private final BooksService booksService;


 @PostMapping("/submit")
 public String submitReview(@ModelAttribute Review review, Model model) {
  Review newReview = reviewsService.createReview(review, review.getBookIsbn());
  return "redirect:/public/book/" + newReview.getBookIsbn();
 }

 @PutMapping("/update")
 public String editReview(@ModelAttribute Review review, Model model) {
  Review updatedReview = reviewsService.updateReview(review);
  return "redirect:/public/book/" + updatedReview.getBookIsbn();
 }

 @GetMapping("/add/{isbn}")
 public String loadReviewForm(@PathVariable("isbn") String isbn, Model model) {

  Book book = booksService.getBookByIsbn(isbn);
  model.addAttribute("title", book.getTitle());
  model.addAttribute("author", book.getAuthor());
  model.addAttribute("thumbnail", book.getThumbnailUrl());

  Review review = new Review();
  review.setBookIsbn(isbn);

  model.addAttribute("bookIsbn", review.getBookIsbn());
  model.addAttribute("review", review);
  return "/auth/book/reviews/add-review";
 }


 @GetMapping("/update/{isbn}")
 public String loadUpdateReviewForm(@PathVariable("isbn") String isbn, Model model) {
  Book book = booksService.getBookByIsbn(isbn);
  model.addAttribute("title", book.getTitle());
  model.addAttribute("author", book.getAuthor());
  model.addAttribute("thumbnail", book.getThumbnailUrl());

  Review review = reviewsService.getReviewByIsbn(isbn);

  if (review == null) {
   model.addAttribute("message", "No review found for the given ISBN");
   return "front/message";
  }

  Review updateData = new Review();
  updateData.setBookIsbn(review.getBookIsbn());
  updateData.setDate(formatForInputField(review.getDate()));
  updateData.setReview(review.getReview());

  model.addAttribute("review", updateData);

  return "/auth/book/reviews/edit-review";
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
