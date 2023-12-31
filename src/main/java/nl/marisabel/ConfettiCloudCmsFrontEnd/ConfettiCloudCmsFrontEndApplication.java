package nl.marisabel.ConfettiCloudCmsFrontEnd;

import nl.marisabel.ConfettiCloudCmsFrontEnd.config.ApiPropertiesConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;


@SpringBootApplication
public class ConfettiCloudCmsFrontEndApplication {

 public static void main(String[] args) {

  SpringApplication.run(ConfettiCloudCmsFrontEndApplication.class, args);
 }


 @Bean
 public RestTemplate restTemplate(RestTemplateBuilder builder) {
  return builder
          .messageConverters(new MappingJackson2HttpMessageConverter())
          .build();
 }

 @Bean
 public MessageSource messageSource() {
  ReloadableResourceBundleMessageSource messageSource
          = new ReloadableResourceBundleMessageSource();
  messageSource.setBasename("classpath:messages");
  messageSource.setDefaultEncoding("UTF-8");
  return messageSource;
 }


 @Bean
 public WebClient webClient() {
  return WebClient.create();
 }


}
