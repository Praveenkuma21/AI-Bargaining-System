package com.praveen.AI.Bargaining;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication()

public class AiBargainingApplication {
	/*
	 * @Value("${openai.key}") private String openaiApiKey;
	 */
	public static void main(String[] args) {
		SpringApplication.run(AiBargainingApplication.class, args);
	}


}
