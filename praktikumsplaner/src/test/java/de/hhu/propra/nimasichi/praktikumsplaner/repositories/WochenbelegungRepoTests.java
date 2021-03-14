package de.hhu.propra.nimasichi.praktikumsplaner.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class WochenbelegungRepoTests {

  @Autowired
  private WochenbelegungRepo wobeRepo;

  private WochenbelegungRepoTests() { }

  @Test
  void testLatestWochenbelegungIsPresent() {
    final var latest
        = wobeRepo.findByHighestId();

    assertTrue(latest.isPresent());
  }

  @Test
  void testLatestWochenbelegungHasCorrectValues() {
    final var latest
        = wobeRepo.findByHighestId().get();

    assertEquals(latest.getId(), 1);
    assertEquals(latest.getZeitslots().size(), 2);
  }

}
