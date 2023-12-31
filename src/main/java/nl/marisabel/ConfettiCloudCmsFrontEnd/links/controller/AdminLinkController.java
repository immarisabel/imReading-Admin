package nl.marisabel.ConfettiCloudCmsFrontEnd.links.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nl.marisabel.ConfettiCloudCmsFrontEnd.links.dto.Link;
import nl.marisabel.ConfettiCloudCmsFrontEnd.links.service.LinkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin/links")
public class AdminLinkController {

 private final LinkService linkService;


 @GetMapping
 public String showLinkManagementPage(@ModelAttribute("link") Link link, Model model) {
  List<Link> links = linkService.getLinkList();
  List<Link> activeLinks = linkService.getActiveLinks();
  model.addAttribute("activeLinks", activeLinks);
  model.addAttribute("links", links);
  return "/auth/link/links";
 }

 @PostMapping("/add")
 public String addLink(@ModelAttribute Link link) {
  linkService.addLink(link);
  return "redirect:/admin/links";
 }

 @GetMapping("/edit/{id}")
 public String editLink(@ModelAttribute("link") Link link, @PathVariable String id, Model model) {
  link = linkService.getLinkById(id);
  model.addAttribute("link", link);
  return "/auth/link/links";
 }

 @PostMapping("/update")
 public String updateLink(@ModelAttribute Link link) {
  linkService.updateLink(link);
  return "redirect:/admin/links";
 }

 @GetMapping("/delete/{id}")
 public String deleteLink(@PathVariable String id) {
  linkService.deleteLink(id);
  return "redirect:/admin/links";
 }
}


