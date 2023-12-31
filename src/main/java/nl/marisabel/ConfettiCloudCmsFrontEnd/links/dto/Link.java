package nl.marisabel.ConfettiCloudCmsFrontEnd.links.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Link {
 private Long id;
 private String name;
 private String url;
 private int displayOrder;
 private boolean showInMenu;

}



//{
//  "id": 0,
//  "name": "string",
//  "url": "string",
//  "displayOrder": 0,
//  "showInMenu": true
//}