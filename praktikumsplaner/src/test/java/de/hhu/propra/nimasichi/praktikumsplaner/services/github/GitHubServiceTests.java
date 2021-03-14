package de.hhu.propra.nimasichi.praktikumsplaner.services.github;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class GitHubServiceTests {

  @Autowired
  private GitHubService ghService;

  private GitHubServiceTests() { }

  @BeforeEach
  void setup() {
    ghService.connect();
  }

  @Test
  @Disabled
  void testSingleUserExists() {
    final var user   = "mawar108";
    final var exists = ghService.doesUserExist(user);

    Assertions.assertTrue(exists);
  }

  @Test
  @Disabled
  void testMultipleUsersExist() {
    final var users = new String[] {
        "mawar108",
        "Nina181",
        "Christopher-Schmitz",
        "Couraxe"
      };
    final var exist = ghService.doUsersExist(users);

    Assertions.assertTrue(exist);
  }

  @Test
  @Disabled
  void testSingleInvalidUserDoesNotExist() {
    final var user   = ";";
    final var exists = ghService.doesUserExist(user);

    Assertions.assertFalse(exists);
  }

  @Test
  @Disabled
  void testMultipleInvalidUsersDoNotExist() {
    final var users = new String[] {
        ";",
        "`",
        "'"
      };
    final var exists = ghService.doUsersExist(users);

    Assertions.assertFalse(exists);
  }

}
