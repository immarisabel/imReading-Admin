package nl.marisabel.ConfettiCloudCmsFrontEnd.pages.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Page {
 private Long id;
 private String title;
 private Date createdDate;
 private Date modifiedDate;
 private String content;
}
