package de.hhu.propra.nimasichi.praktikumsplaner.web.form;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.zeitslot.Zeitslot;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.ZeitslotRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.github.GitHubService;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@SuppressWarnings("PMD.LawOfDemeter")
public class RestplatzBelegenForm {


  private final long zeitslotId;
  private final List<String> alerts = new ArrayList<>();
  private final ZeitslotRepo zsRepo;
  private final String login;
  private Zeitslot zeitslot = new Zeitslot();

  private boolean isValid = true;


  public void validateForm(final GitHubService gitHubService) {
    checkHandle(gitHubService);
    checkRestplatze();
  }

  private void checkRestplatze() {
    final var maybeZeitslot = zsRepo.findZeitslotById(zeitslotId);
    if (maybeZeitslot.isEmpty()) {
      alerts.add("Es ist ein Fehler aufgetreten (maybeZeitslot.isEmpty() = true!)");
      isValid = false;
    } else {
      zeitslot = maybeZeitslot.get();
      if (!zeitslot.hatRestplatze()) {
        alerts.add("Restplatz schon vergeben");
        isValid = false;
      }
    }
  }

  private void checkHandle(final GitHubService gitHubService) {
    if (!gitHubService.doesUserExist(login)) {
      alerts.add("Der Github Handle " + login + " ist ungültig");
      isValid = false;
    }
  }

}
