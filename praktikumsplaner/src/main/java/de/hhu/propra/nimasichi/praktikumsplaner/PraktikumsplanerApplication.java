package de.hhu.propra.nimasichi.praktikumsplaner;

import de.hhu.propra.nimasichi.praktikumsplaner.github.GithubConnector;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@SuppressWarnings("PMD.UseUtilityClass")
public class PraktikumsplanerApplication {

	public static void main(final String[] args) {
		SpringApplication.run(PraktikumsplanerApplication.class, args);
	}

	@Bean
	public CommandLineRunner init() {
		return args -> {
			GithubConnector.connect();
		};
	}

}
