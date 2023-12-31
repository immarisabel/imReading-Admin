package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class Shelf {

 private Long id;
 private String shelfName;
private List<Book> books;
private  int bookCount;

}
