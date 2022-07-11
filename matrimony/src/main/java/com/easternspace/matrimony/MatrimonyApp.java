package com.easternspace.matrimony;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 
 * @author E Harish
 *
 * 8:29:49 pm
 */
@SpringBootApplication
@EnableJpaAuditing
public class MatrimonyApp {
	public static void main(String[] args) {
		SpringApplication.run(MatrimonyApp.class, args);
	}

}
