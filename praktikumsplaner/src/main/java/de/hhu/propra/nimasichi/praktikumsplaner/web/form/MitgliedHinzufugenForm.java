package de.hhu.propra.nimasichi.praktikumsplaner.web.form;

import de.hhu.propra.nimasichi.praktikumsplaner.services.github.GitHubService;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MitgliedHinzufugenForm {

  private final int zeitslotId;
  private final List<String> mitglieder; // GitHub-Handles
  private final List<String> alerts = new ArrayList<>();
  private final String gruppenName;

  private boolean isValid = true;

  private static final transient int MIN_GRUPPEN_SIZE = 2;

  public void validateForm(final GitHubService gitHubService,
                           final int maxSize) {
    checkHandles(gitHubService);
    checkSize(maxSize);
    checkName();
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

}
