package nl.marisabel.ConfettiCloudCmsFrontEnd.pages.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nl.marisabel.ConfettiCloudCmsFrontEnd.pages.dto.Page;
import nl.marisabel.ConfettiCloudCmsFrontEnd.pages.service.PagesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("public/page")
@RequiredArgsConstructor
public class PageController {

 private final PagesService pages;

 @GetMapping("/{id}")
 public String getPageById(@PathVariable("id") String id, Page page, Model model) {
  page = pages.getPageById(id);
  model.addAttribute("page", page);
  return "front/page";
 }




}
