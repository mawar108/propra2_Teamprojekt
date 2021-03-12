package de.hhu.propra.nimasichi.praktikumsplaner.utility;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.wochenbelegung.Zeitslot;

import java.util.List;
import java.util.Optional;

public class GruppenanmeldungAlertHelper {

  public static Zeitslot getZeitslotOrAddAlert(
      final Optional<Zeitslot> maybeZeitslot,
      final List<String> alerts) {
    Zeitslot zeitslot = null;
    if (maybeZeitslot.isEmpty()) {
      alerts.add("Es ist ein Fehler aufgetreten (zeitslot.isEmpty() = true!)");
    } else {
      zeitslot = maybeZeitslot.get();
    }
    return zeitslot;
  }

  public static void checkUserExistsOrAlert(final List<String> alerts) {

  }

}
