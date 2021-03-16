package de.hhu.propra.nimasichi.praktikumsplaner;

import com.google.common.collect.Lists;
import de.hhu.propra.nimasichi.praktikumsplaner.services.github.GitHubService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties
@SuppressWarnings({"PMD.UseUtilityClass", "PMD.AtLeastOneConstructor"})
public class PraktikumsplanerApplication {

  public static void main(final String[] args) {
    SpringApplication.run(PraktikumsplanerApplication.class, args);
  }

  @Bean
  public CommandLineRunner init(final GitHubService ghService) {
    return args -> {
      ghService.connect();
      partition();
    };
  }

  public static void partition() {
    final var min = 3;
    final var max = 5;
    final var studenten
        = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");
    List<List<String>> partition;

    Collections.shuffle(studenten);

    for (int i = 0; min + i <= max; ++i) {
      partition = Lists.partition(studenten, min + i);
      System.out.println(partition);

      if (istOk(partition)) {
        break;
      }
    }
  }

  private static boolean istOk(
      final List<List<String>> partition
  ) {
    final var min = 3;
    final var max = 5;
    boolean ok = true;

    for (final var list : partition) {
      if (list.size() < min || list.size() > max) {
        ok = false;
        break;
      }
    }

    return ok;
  }

}
