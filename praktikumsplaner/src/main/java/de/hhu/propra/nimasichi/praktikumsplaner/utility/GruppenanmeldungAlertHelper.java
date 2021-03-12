package de.hhu.propra.nimasichi.praktikumsplaner.utility;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.Zeitslot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ZEITSLOT_MODEL_NAME;

public class GruppenanmeldungAlertHelper {

  public static Zeitslot getZeitslotOrAddAlert(final Optional<Zeitslot> maybeZeitslot, final List<String> alerts) {
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
