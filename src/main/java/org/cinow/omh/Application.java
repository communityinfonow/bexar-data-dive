package org.cinow.omh;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Spring Boot app.
 * 
 * @author brian
 */
@SpringBootApplication
public class Application {

	/**
	 * Main method.
	 * @param args the command line arguments
	 * @throws IOException if there is an error
	 */
	public static void main(String[] args) throws IOException {
		SpringApplication.run(Application.class, args);
	}

}
