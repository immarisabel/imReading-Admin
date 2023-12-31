package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReadingData {

 private String bookIsbn;
 @DateTimeFormat(pattern = "yyyy-MM-dd")
 private String startedDate;
 @DateTimeFormat(pattern = "yyyy-MM-dd")
 private String finishedDate;
 private ReadingStatus status;
 private int currentPage;
 private int rating;
 private boolean favorite;
}
