package com.dp.palace;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PalaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PalaceApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer(@Value("${allow.domain.1}") String allowDomain1,
										   @Value("${allow.domain.2}") String allowDomain2) {
		String[] allowDomains = new String[2];
		allowDomains[0] = allowDomain1;
		allowDomains[1] = allowDomain2;

		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(allowDomains);
			}
		};
	}
}
