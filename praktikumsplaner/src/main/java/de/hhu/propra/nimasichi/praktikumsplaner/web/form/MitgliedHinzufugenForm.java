package de.hhu.propra.nimasichi.praktikumsplaner.web.form;

import de.hhu.propra.nimasichi.praktikumsplaner.repositories.ZeitslotRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.github.GitHubService;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@SuppressWarnings({
    "PMD.LawOfDemeter"
})
public class MitgliedHinzufugenForm {

  private final long zeitslotId;
  private final List<String> mitglieder; // GitHub-Handles
  private final List<String> alerts = new ArrayList<>();
  private final String gruppenName;

  private final transient ZeitslotRepo zsRepo;

  private boolean isValid = true;

  private static final transient int MIN_GRUPPEN_SIZE = 2;

  public void validateForm(final GitHubService gitHubService,
                           final int maxSize) {
    checkHandles(gitHubService);
    checkSize(maxSize);
    checkName();
    checkRaceCondition();
  }

  private void checkName() {
    if (gruppenName.isBlank()) {
      alerts.add("Gruppenname darf nicht leer sein");
      isValid = false;
    }
  }

  private void checkSize(final int maxSize) {
    if (mitglieder.size() > maxSize) {
      alerts.add("Es dürfen maximal nur " + maxSize + " Mitglieder teilnehmen");
      isValid = false;
    } else if (mitglieder.size() < MIN_GRUPPEN_SIZE) {
      alerts.add("In einer Gruppe müssen mindestens zwei Personen sein");
      isValid = false;
    }
  }

  private void checkHandles(final GitHubService gitHubService) {
    for (final var mitglied : mitglieder) {
      if (!gitHubService.doesUserExist(mitglied)) {
        alerts.add("Der Github Handle " + mitglied + " ist ungültig");
        isValid = false;
      }
    }
  }

  private void checkRaceCondition() {
    final var maybeZeitslot
        = zsRepo.findZeitslotById(zeitslotId);

    // Wenn isEmpty(), dann redirectet der Controller
    if (maybeZeitslot.isPresent()) {
      final var zeitslot = maybeZeitslot.get();
      if (!zeitslot.hatFreienGruppenplatz()) {
        alerts.add("Du warst zu langsam, "
            + "eine andere Gruppe hat den Platz schon.");
        isValid = false;
      }
    }

  }

}
