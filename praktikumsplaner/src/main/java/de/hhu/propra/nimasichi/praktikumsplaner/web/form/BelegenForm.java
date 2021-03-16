package de.hhu.propra.nimasichi.praktikumsplaner.web.form;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.zeitslot.Zeitslot;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.ZeitslotRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.github.GitHubService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig.UbungswocheConfigService;

import java.util.ArrayList;
import java.util.List;

public abstract class BelegenForm {

  protected final GitHubService ghService;
  protected final String login;
  protected final long zeitslotId;
  protected final ZeitslotRepo zsRepo;
  protected Zeitslot zeitslot;
  protected boolean isValid;
  protected final List<String> alerts;
  private final UbungswocheConfigService ubwoService;

  public BelegenForm(final GitHubService ghService,
                     final String login,
                     final long zeitslotId,
                     final ZeitslotRepo zeitslotRepo,
                     final UbungswocheConfigService ubwoService) {
    this.ghService = ghService;
    this.login = login;
    this.zeitslotId = zeitslotId;
    this.zsRepo = zeitslotRepo;
    this.zeitslot = new Zeitslot();
    this.isValid = true;
    this.alerts = new ArrayList<>();
    this.ubwoService = ubwoService;
  }

  public void validateForm() {
    checkHandle();
  }

  private void checkHandle() {
    if (!ghService.doesUserExist(login)) {
      alerts.add("Der Github Handle " + login + " ist ungültig");
      isValid = false;
    }
  }

  public final boolean isValid() {
    return isValid;
  }

  public final List<String> getAlerts() {
    return alerts;
  }

  public final Zeitslot getZeitslot() {
    return zeitslot;
  }

  protected final void checkConfig(final int gruppenmodus) {
    final var maybeConfig = ubwoService.getAktuelleUbungswocheConfig();

    if (maybeConfig.isPresent()) {
      if (gruppenmodus != maybeConfig.get().getModus()) {
        alerts.add("Ein Fehler ist aufgetreten: 400");
        isValid = false;
      }
    } else {
      alerts.add("Ein Fehler ist aufgetreten: 400");
      isValid = false;
    }
  }

}
