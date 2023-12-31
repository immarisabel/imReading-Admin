package nl.marisabel.ConfettiCloudCmsFrontEnd.imreading.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
 private String isbn;
 private String title;
 private String author;
 private int pages;
 private String thumbnailUrl;
 private String selfLink;
 private List<String> shelves;
 private Review review;
 private ReadingData readingData;
}
