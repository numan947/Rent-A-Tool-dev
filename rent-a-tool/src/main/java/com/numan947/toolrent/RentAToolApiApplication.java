package com.numan947.toolrent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // this annotation is used to enable JPA Auditing
public class RentAToolApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentAToolApiApplication.class, args);
	}

}
