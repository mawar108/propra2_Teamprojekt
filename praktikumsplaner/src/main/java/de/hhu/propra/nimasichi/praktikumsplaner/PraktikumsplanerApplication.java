package de.hhu.propra.nimasichi.praktikumsplaner;

import de.hhu.propra.nimasichi.praktikumsplaner.services.github.GitHubService;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.WochenbelegungRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@SuppressWarnings({"PMD.UseUtilityClass", "PMD.AtLeastOneConstructor"})
public class PraktikumsplanerApplication {

  public static void main(final String[] args) {
    SpringApplication.run(PraktikumsplanerApplication.class, args);
  }

  @Bean
  public CommandLineRunner init(final GitHubService ghService,
                                final UbungswocheConfigRepo ubWoRepo,
                                final WochenbelegungRepo wbRepo) {
    return args -> {
      ghService.connect();
    };
  }

}
