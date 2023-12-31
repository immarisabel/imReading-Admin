 package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.controller;

 import lombok.RequiredArgsConstructor;
 import lombok.extern.log4j.Log4j2;
 import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.Logs;
 import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service.LogsService;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.*;

 @Controller
 @RequiredArgsConstructor
 @RequestMapping("admin/log")
 @Log4j2
 public class AdminLogController {

  private final LogsService logs;


  @GetMapping("/add")
  public String getFormToAddLog(Model model) {
   Logs readingLog = new Logs();
   model.addAttribute("readingLog", readingLog);
   return "auth/book/logs/add-log";
  }

  @PostMapping("/add")
  public String addANewLog(@ModelAttribute("readingLog") Logs readingLog) {
   Logs newLog = logs.addANewLog(readingLog);

   if (newLog != null && newLog.getIsbn() != null) {
    return "redirect:/public/book/" + newLog.getIsbn();
   } else {
    return "auth/book-creation-failed";
   }
  }


  @PostMapping("/delete/{id}")
  public String deleteLog(
          @PathVariable("id") Long id,
          @RequestHeader(name = "Referer", required = false) String referer) {
   logs.deleteLogById(id);
   return "redirect:" + (referer != null ? referer : "/default");
  }

  @GetMapping("/edit/{id}")
  public String getFormToEditLog(@PathVariable("id") Long id, Logs logToEdit, Model model) {
   logToEdit = logs.getLogById(logToEdit, id);
   log.info("🔵 logToEdit: " + id);
   log.info("🔵 logToEdit: " + logToEdit);
   model.addAttribute("log", logToEdit);
   return "auth/book/logs/edit-log";
  }



  @PostMapping("/update/{id}")
  public String updateLog(
          @PathVariable("id") Long id,
          @ModelAttribute("log") Logs logToEdit,
          Model model) {
   Logs updatedLog = logs.updateLog(logToEdit, id);

   if (updatedLog != null && updatedLog.getIsbn() != null) {
    return "redirect:/public/book/" + updatedLog.getIsbn();
   } else {
    return "auth/log-update-failed";
   }
  }


 }



