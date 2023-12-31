package nl.marisabel.ConfettiCloudCmsFrontEnd.pages.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nl.marisabel.ConfettiCloudCmsFrontEnd.pages.dto.Page;
import nl.marisabel.ConfettiCloudCmsFrontEnd.pages.service.PagesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin/page")
@RequiredArgsConstructor
public class AdminPageController {

 private final PagesService pages;

 @PostMapping("/add")
 public String createPage(@ModelAttribute Page page, Model model) {
  Page createdPage = pages.createPage(page);
  if (createdPage != null && createdPage.getId() != null) {
   return "redirect:/public/page/" + createdPage.getId();
  } else {
   return "/auth/page-creation-failed";
  }
 }

 @GetMapping("/add")
 public String loadCreatePageForm(Model model) {
  Page page = new Page();
  model.addAttribute("page", page);
  return "/auth/page/add-page";
 }

 @GetMapping("/update/{id}")
 public String loadPageToUpdate(@PathVariable("id") String id, Page page, Model model) {
  page = pages.getPageById(id);
  model.addAttribute("page", page);
  return "/auth/page/update-page";
 }

 @PostMapping("/update/{id}")
 public String updatePage(@PathVariable("id") String id, Page page, Model model) {
  Page updatedPage = pages.updatePage(page);
  model.addAttribute("page", updatedPage);

  if (updatedPage != null && updatedPage.getId() != null) {
   String redirectUrl = "/public/page/" + updatedPage.getId();
   return "redirect:" + redirectUrl;
  } else {
   return "/auth/page/page-update-failed";
  }
 }

 @PostMapping("/delete/{id}")
 public String deletePage(@PathVariable("id") String id) {
  pages.deletePage(id);
  return "redirect:../list";
 }

 @GetMapping("/list")
 public String getPagesList(Page page, Model model) {
  List<Page> pageList = pages.getPagesList();
  model.addAttribute("pageList", pageList);
  return "/auth/page/page-list";
 }

}
