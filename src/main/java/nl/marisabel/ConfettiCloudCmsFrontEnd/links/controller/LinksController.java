package nl.marisabel.ConfettiCloudCmsFrontEnd.links.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.marisabel.ConfettiCloudCmsFrontEnd.links.dto.Link;
import nl.marisabel.ConfettiCloudCmsFrontEnd.links.service.LinkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("public/links")
@Log4j2
public class LinksController {

 private final LinkService linkService;


 @GetMapping
 @ResponseBody
 public List<Link> showActiveLinksOnNavigation() {
  return linkService.getActiveLinks ();
 }
}
