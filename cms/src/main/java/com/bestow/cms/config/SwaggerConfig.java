package com.bestow.cms.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Autowired
	private Environment env;
	
	@Bean
	public Docket api() {
		final Set<String> protocols = new HashSet<>();
		protocols.add("http");
		protocols.add("https");
		return new Docket(DocumentationType.SWAGGER_2).groupName("public-api").apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.bestow.cms.controller"))
				.paths(PathSelectors.any())
				.build().pathMapping("")
				.protocols(protocols).host(env.getProperty("swagger.host.url", ""));
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Vaya Cron API")
				.description("Vaya Cron jayam API reference for developers")
				.termsOfServiceUrl("http://vayacron.in")
				.contact("vayacron@vayaindia.com").license("Vaya FinServ License")
				.licenseUrl("vayacron@vayaindia.com").version("1.0").build();
	}

}
