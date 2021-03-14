package de.hhu.propra.nimasichi.praktikumsplaner.github;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class GitHubServiceTests {

  @Autowired
  private GitHubService ghService;

  private GitHubServiceTests() { }

  @BeforeEach
  void setup() {
    assertDoesNotThrow(ghService::connect);
  }

  @Test
  void testSingleUserExists() {
    final var user   = "mawar108";
    final var exists = ghService.doesUserExist(user);

    assertTrue(exists);
  }

  @Test
  void testMultipleUsersExist() {
    final var users = new String[] {
        "mawar108",
        "Nina181",
        "Christopher-Schmitz",
        "Couraxe"
    };
    final var exist = ghService.doUsersExist(users);

    assertTrue(exist);
  }

  @Test
  void testSingleInvalidUserDoesNotExist() {
    final var user   = ";";
    final var exists = ghService.doesUserExist(user);

    assertFalse(exists);
  }

  @Test
  void testMultipleInvalidUsersDoNotExist() {
    final var users = new String[] {
        ";",
        "`",
        "'"
    };
    final var exists = ghService.doUsersExist(users);

    assertFalse(exists);
  }

}