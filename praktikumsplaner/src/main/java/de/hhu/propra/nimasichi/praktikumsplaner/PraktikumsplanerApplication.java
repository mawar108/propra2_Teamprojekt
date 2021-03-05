package de.hhu.propra.nimasichi.praktikumsplaner;

import de.hhu.propra.nimasichi.praktikumsplaner.github.GitHubService;
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
	public CommandLineRunner init(final GitHubService ghService) {
		return args -> {
			ghService.connect();

//			final var users = new String[] { "mawar108", "Christopher-Schmitz", "Nina181", "Couraxe" };
//
//			ghService.createRepository("test", users);
		};
	}

}
