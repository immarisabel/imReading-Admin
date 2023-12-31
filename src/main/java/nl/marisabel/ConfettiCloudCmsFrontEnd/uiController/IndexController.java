package nl.marisabel.ConfettiCloudCmsFrontEnd.uiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.ApiTokenManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class IndexController {

 private final ApiTokenManager token;

 @RequestMapping("/")
 public String index(Model model) {
  return "index";
 }

 @RequestMapping("/template")
 public String getTemplatePageForTesting(Model model) {
  return "imreading-template";
 }

}
