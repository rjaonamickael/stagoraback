package com.stagora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@PropertySource("classpath:configMail.properties") 
@EnableSpringDataWebSupport

public class StagorabackApplication{

	public static void main(String[] args) {
		SpringApplication.run(StagorabackApplication.class, args);
	}


}
