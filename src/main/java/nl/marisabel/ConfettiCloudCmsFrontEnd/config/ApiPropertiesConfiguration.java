package nl.marisabel.ConfettiCloudCmsFrontEnd.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiPropertiesConfiguration {

 @Value("${api.base-path.url}")
 String apiUrlRoot;

 @Value("${api.base-path.pages}")
 String pagesUrl;

 @Value("${api.base-path.books}")
 String booksUrl;

 @Value("${api.base-path.menu}")
 String menuUrl;


 @Bean
 public ApiProperties apiProperties() {
  ApiProperties properties = new ApiProperties();
  properties.setApiUrlRoot(apiUrlRoot);
  properties.setPagesUrl(pagesUrl);
  properties.setBooksUrl(booksUrl);
  properties.setMenuUrl(menuUrl);
  properties.setUsername("immarisabel");
  properties.setPassword("!aNEWhapieHM.in2023");
  return properties;
 }

}
