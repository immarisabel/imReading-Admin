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
public class Review {

 private String bookIsbn;
 @DateTimeFormat(pattern = "yyyy-MM-dd")
 private String date;
 private String review;


}
