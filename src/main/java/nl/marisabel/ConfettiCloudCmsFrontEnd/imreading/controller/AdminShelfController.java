package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.Shelf;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service.BooksService;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service.ShelvesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/shelf")
@RequiredArgsConstructor
@Log4j2
public class AdminShelfController {

 private final ShelvesService shelves;
 private  final BooksService books;

 @PostMapping("/add")
 public String createShelf(@ModelAttribute Shelf shelf, Model model) {
  Shelf createdShelf = shelves.createShelf(shelf);
  log.info("🟢 shelf to create: " + shelf.getShelfName());
  if (createdShelf != null && createdShelf.getShelfName() != null) {
   return "redirect:/admin/shelf/admin";
  } else {
   return "/auth/shelf-creation-failed";
  }
 }

 @GetMapping("/add")
 public String loadCreateShelfForm(Model model) {
  Shelf shelf = new Shelf();
  model.addAttribute("shelf", shelf);
  return "/auth/shelf/add-shelf";
 }

 @GetMapping("/update/{shelfName}")
 public String loadShelfToUpdate(@PathVariable("shelfName") String name, Model model) {
  Shelf shelf = shelves.getShelfByName(name);
  model.addAttribute("shelf", shelf);
  return "/auth/shelf/shelf-admin";
 }

 @PostMapping("/update/{name}")
 public String updateShelf(@PathVariable("name") String name, Shelf shelf, Model model) {


  Shelf updatedShelf = new Shelf();
  updatedShelf = shelves.getShelfByName(name);
  log.info("🟢 updating shelf: " + updatedShelf.getShelfName());
  log.info("🟢 shelf to update: " + updatedShelf.getId());
  updatedShelf.setShelfName(shelf.getShelfName());
  shelves.updateShelf(updatedShelf);
  model.addAttribute("shelf", updatedShelf);

  if (updatedShelf != null && updatedShelf.getShelfName() != null) {
   String redirectUrl = "/admin/shelf/admin";
   return "redirect:" + redirectUrl;
  } else {
   return "/auth/shelf/shelf-update-failed";
  }
 }

 @PostMapping("/delete/{name}")
 public String deleteShelf(@PathVariable("name") String name, Model model) {
  shelves.deleteShelf(name);
  model.addAttribute("shelfList", shelves.getShelvesList());
  return "redirect:/admin/shelf/admin";
 }


 @GetMapping("/list")
 @ResponseBody
 public List<Shelf> getShelvesList(Shelf shelf, Model model) {



  return shelves.getShelvesList();
 }


@GetMapping("/admin")
public String getAdminPageForShelves(@ModelAttribute Shelf shelf, Model model) {
  log.info("🔵 getting page");
    Map<String, Long> shelfBookCountMap = books.getBooksListWithBookCount();
log.info("📚 shelfBookCountMap: " + shelfBookCountMap.toString());
    model.addAttribute("shelfBookCountMap", shelfBookCountMap);

    return "/auth/shelf/shelf-admin";
}




}