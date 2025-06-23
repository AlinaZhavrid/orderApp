package com.alina.orderapp;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderappApplication {
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()
					.load();

		dotenv.entries().forEach(e ->
				System.setProperty(e.getKey(), e.getValue()));

		SpringApplication.run(OrderappApplication.class, args);
	}
}
