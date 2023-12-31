package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BooksInShelves {
 private String isbn;
 private List<String> shelves;

}
