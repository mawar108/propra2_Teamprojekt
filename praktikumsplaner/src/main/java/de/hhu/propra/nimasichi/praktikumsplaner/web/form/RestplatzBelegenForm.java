package de.hhu.propra.nimasichi.praktikumsplaner.web.form;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.wochenbelegung.Wochenbelegung;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.wochenbelegung.Zeitslot;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.WochenbelegungRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.github.GitHubService;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@SuppressWarnings("PMD.LawOfDemeter")
public class RestplatzBelegenForm {

  private final int zeitslotId;
  private final List<String> alerts = new ArrayList<>();
  private final WochenbelegungRepo wobeRepo;
  private final String login;
  private Zeitslot zeitslot = new Zeitslot();

  private boolean isValid = true;


  public void validateForm(final GitHubService gitHubService) {
    checkHandle(gitHubService);
    checkRestplatze();
  }

  private void checkRestplatze() {
    final var maybeZeitslot
        = wobeRepo.findZeitslotById(zeitslotId);
    if (maybeZeitslot.isEmpty()) {
      alerts.add("Es ist ein Fehler aufgetreten (maybeZeitslot.isEmpty() = true!)");
      isValid = false;
    } else {
      zeitslot = maybeZeitslot.get();
      if (!Wochenbelegung.zeitslotHatRestplatze(zeitslot)) {
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
