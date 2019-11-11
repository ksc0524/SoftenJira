package com.softenware.jira;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SoftenJiraApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoftenJiraApplication.class, args);
	}
	
	@Autowired
	private TestService jira;

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			jira.test();
		};
	}
}
