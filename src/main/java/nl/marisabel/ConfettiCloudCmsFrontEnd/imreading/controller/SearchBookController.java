package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.controller;

import lombok.RequiredArgsConstructor;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.UriGenerator;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.SearchBook;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/search")
public class SearchBookController {

 private final UriGenerator uri;
 private final SearchService searchService;
private String htmlPage = "/front/search";

// /app/imreading/v1/search?QUERY


 @GetMapping
 public String showSearchForm(Model model) {
  model.addAttribute("query", "");
  model.addAttribute("bookList", new ArrayList<SearchBook>());
  return htmlPage;
 }

 @PostMapping
 public String searchForBooks(@RequestParam String query, Model model) {
  List<SearchBook> bookList = searchService.getResultsForBooksSearch(query);
  model.addAttribute("query", query);
  model.addAttribute("bookList", bookList);
  return htmlPage;
 }




}
