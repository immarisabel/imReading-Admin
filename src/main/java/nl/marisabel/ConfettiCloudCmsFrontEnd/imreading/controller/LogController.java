package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.controller;

import lombok.RequiredArgsConstructor;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.Logs;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service.LogsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("public/log")
@RequiredArgsConstructor
public class LogController {

 private final LogsService logs;


 @GetMapping("/list/{isbn}")
 @ResponseBody
 public List<Logs> getAllLogsForBook(@PathVariable("isbn") String isbn, Model model) {
 return logs.getLogsForBookAsList(isbn);
 }

 @GetMapping("{id}")
 @ResponseBody
 public Logs getLogById(@PathVariable("id") Long id, Logs log, Model model) {
 return logs.getLogById(log, id);
 }

}
