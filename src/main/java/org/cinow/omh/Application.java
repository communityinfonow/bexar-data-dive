package org.cinow.omh;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Application.class, args);
		PostgreSQLContainer postgres = new PostgreSQLContainer<>("postgres")
            .withDatabaseName("foo")
            .withUsername("it_user")
            .withPassword("it_pass")
            .withInitScript("sql/init_postgres.sql");
	}

}
