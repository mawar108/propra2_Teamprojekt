package de.hhu.propra.nimasichi.praktikumsplaner;

import de.hhu.propra.nimasichi.praktikumsplaner.services.github.GitHubService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
      partition(6);
    };
  }


  public static void partition(int n) {
    int min = 3;
    int max = 5;
    List<String> studenten = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");

    List<List<String>> partition;
    for (int i = 0; min+i <= max; i++) {
      partition = Lists.partition(studenten, min+i);
      System.out.println(partition);
      if (istOk(partition)) break;
    }


  }

  private static boolean istOk(List<List<String>> partition) {
    int min = 3;
    int max = 5;

    for (var list : partition) {
      if (list.size() < min || list.size() > max) {
        return false;
      }

    }
    return true;
  }

}
