package com.citi.payment.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final String BASE_PACKAGE = "com.citi";
	public static final String TITLE = "CITI PAYMENTS";
	public static final String DESCRIPTION = "Argentina-Payments microservices documentation";
	public static final String VERSION = "1.0";
	private static final Set<String> DEFAULT_PRODUCES_CONSUMES = new HashSet<>(
			Arrays.asList(MediaType.APPLICATION_JSON_VALUE));

	@Bean
	public Docket productApi() {

		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(regex("/api/v1.*")).build().apiInfo(getApiInfo()).produces(DEFAULT_PRODUCES_CONSUMES)
				.consumes(DEFAULT_PRODUCES_CONSUMES);
	}

	private ApiInfo getApiInfo() {

		return new ApiInfo(TITLE, "DESCRIPTION", VERSION, "",null,"", "", Collections.emptyList());
	}
}