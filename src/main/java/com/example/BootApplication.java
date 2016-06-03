package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;


@SpringBootApplication 
@ConfigurationProperties(prefix = "spring.datasource")
public class BootApplication {
	

    
 public static void main(String[] args)
 {
	 
	SpringApplication.run(BootApplication.class, args);
		
		
 }
	
	
	  
}
