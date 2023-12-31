package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Logs {
 private Long id;
 private String isbn;
 @DateTimeFormat(pattern = "yyyy-MM-dd")
 private String date;
 private int page;
 private boolean favorite;
 private String log;

}

