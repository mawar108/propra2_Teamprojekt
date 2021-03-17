package de.hhu.propra.nimasichi.praktikumsplaner.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.NumericConstants.MODUS_GRUPPE;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.NumericConstants.MODUS_INDIVIDUAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UbungswocheRepoTests {

  @Autowired
  private UbungswocheConfigRepo ubConfRepo;

  @MockBean
  private ZeitslotRepo repo;

  private UbungswocheRepoTests() { }

  @Test
  void testLatestConfigIsPresent() {
    final var latest
        = ubConfRepo.findByHighestId();

    assertTrue(latest.isPresent());
  }

  @Test
  void testLatestConfigHasCorrectValues() {
    final var latest
        = ubConfRepo.findByHighestId().get();

    assertEquals(latest.getAnmeldestart().getDayOfMonth(), 14);
    assertEquals(latest.getAnmeldeschluss().getDayOfMonth(), 17);
    assertEquals(latest.getName(), "Pue 8");
    assertEquals(latest.getModus(), MODUS_INDIVIDUAL);
    assertEquals(latest.getMinPersonen(), 3);
    assertEquals(latest.getMaxPersonen(), 5);
  }

}
