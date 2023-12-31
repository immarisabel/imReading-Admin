package nl.marisabel.ConfettiCloudCmsFrontEnd.uiController;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nl.marisabel.ConfettiCloudCmsFrontEnd.config.ApiTokenManager;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto.Book;
import nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.service.BooksService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FrontController {

 private final ApiTokenManager token;
 private final BooksService books;

 @RequestMapping("/home")
 public String getHomePage(Model model, HttpServletResponse response) {
  Cookie existingTokenCookie = token.retrieveTokenFromCookie();
  if (existingTokenCookie == null) {
   String newToken = token.getAccessToken();
   Cookie newTokenCookie = new Cookie("imReading", newToken);
   response.addCookie(newTokenCookie);
   model.addAttribute("token", newToken);
  }
  return "front/entry";
 }


}
