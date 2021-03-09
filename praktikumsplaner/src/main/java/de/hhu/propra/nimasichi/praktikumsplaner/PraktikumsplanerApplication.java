package de.hhu.propra.nimasichi.praktikumsplaner;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.github.GitHubService;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@SuppressWarnings({"PMD.UseUtilityClass", "PMD.AtLeastOneConstructor"})
public class PraktikumsplanerApplication {

  public static void main(final String[] args) {
    SpringApplication.run(PraktikumsplanerApplication.class, args);
  }

  @Bean
  public CommandLineRunner init(final GitHubService ghService, final UbungswocheConfigRepo repo) {
    return args -> {
      ghService.connect();
      List<PraktischeUbungswocheConfig> all = repo.findAll();
      System.out.println(all);
    };
  }

}
