package com.bestow.cms;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableJpaAuditing
public class CmsApp {
	
	public static void main(String args[]) {
		SpringApplication.run(CmsApp.class, args);
	}
	
	@Bean
	public CorsFilter corsFilter() {
	    CorsConfiguration config = new CorsConfiguration();
	    config.addAllowedOrigin("*");
	    config.addAllowedHeader("*");
	    config.setAllowedMethods(Arrays.asList("OPTIONS", "GET", "POST"));

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/api/**", config);

	    return new CorsFilter(source);
	}

}
