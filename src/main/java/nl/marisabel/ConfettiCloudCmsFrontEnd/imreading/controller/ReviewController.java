package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.controller;


import lombok.RequiredArgsConstructor;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.Review;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service.ReviewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/public/review")
public class ReviewController {

 private final ReviewsService reviewsService;

 @GetMapping("/{isbn}")
 @ResponseBody
 public Review getReviewByIsbn(@PathVariable("isbn") String isbn, Review review, Model model) {
  return reviewsService.getReviewByIsbn(isbn);
 }

 @GetMapping("/list")
 public String getReviewList(Model model) {
  List<Review> reviews = reviewsService.getReviewList();
  model.addAttribute("reviews", reviews);
  return "/front/review-list";
 }


}
