package com.example.demo;

import com.example.demo.models.Delivery;
import com.example.demo.models.Meal;
import com.example.demo.models.Restaurant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({Restaurant.class, Meal.class, Delivery.class})
@ConfigurationPropertiesScan
public class ServByteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServByteApplication.class, args);
	}

}
