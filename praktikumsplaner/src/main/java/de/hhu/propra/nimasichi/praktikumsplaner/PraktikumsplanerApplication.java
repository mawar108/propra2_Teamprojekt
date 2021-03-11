package de.hhu.propra.nimasichi.praktikumsplaner;

import de.hhu.propra.nimasichi.praktikumsplaner.github.GitHubService;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.WochenbelegungRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
@SuppressWarnings({"PMD.UseUtilityClass", "PMD.AtLeastOneConstructor"})
public class PraktikumsplanerApplication {

  public static void main(final String[] args) {
    SpringApplication.run(PraktikumsplanerApplication.class, args);
  }

  @Bean
  public CommandLineRunner init(final GitHubService ghService,
                                final UbungswocheConfigRepo repo,
                                final WochenbelegungRepo wbrepo) {
    return args -> {
      ghService.connect();
      final var all = repo.findAll();
      System.out.println("findAll() = " + all);
      final var wochenbelegung = wbrepo.findAll();
      System.out.println(wochenbelegung);

      final var users = new String[] {
          "mawar108",
          "Nina181",
          "Christopher-Schmitz",
          "Couraxe"
      };
      final var exists = ghService.doUsersExist(users);

      System.out.println("doUsersExist("
          + Arrays.toString(users) + ") = " + exists);
    };
  }

}
