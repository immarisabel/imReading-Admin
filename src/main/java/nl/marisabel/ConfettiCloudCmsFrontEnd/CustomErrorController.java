package nl.marisabel.ConfettiCloudCmsFrontEnd;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class CustomErrorController {

 @ExceptionHandler(Throwable.class)
 @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
 public String handleException(HttpServletRequest request, Throwable ex, Model model) {
  model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
  model.addAttribute("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
  model.addAttribute("message", ex.getMessage());
  model.addAttribute("path", request.getRequestURI());

  return "error";
 }
}