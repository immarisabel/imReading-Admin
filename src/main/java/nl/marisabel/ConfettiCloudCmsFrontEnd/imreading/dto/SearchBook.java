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
public class SearchBook {
 private String isbn;
 private String title;
 private String author;
 private int pages;
 private String thumbnailUrl;
 private String selfLink;
 private List<Shelf> shelves;

}
