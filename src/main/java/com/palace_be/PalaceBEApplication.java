package com.palace_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PalaceBEApplication {

	public static void main(String[] args) {
		SpringApplication.run(PalaceBEApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		String[] allowDomains = new String[2];
		allowDomains[0] = "http://localhost:4200";
		allowDomains[1] = "http://localhost:8080";

		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(allowDomains);
			}
		};
	}
}
