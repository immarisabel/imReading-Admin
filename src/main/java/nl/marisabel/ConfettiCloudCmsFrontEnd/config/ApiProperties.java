package nl.marisabel.ConfettiCloudCmsFrontEnd.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiProperties {

 private  String username;
 private  String password;
 private  String apiUrlRoot;
 private  String pagesUrl;
 private  String booksUrl;
 private  String menuUrl;

}
