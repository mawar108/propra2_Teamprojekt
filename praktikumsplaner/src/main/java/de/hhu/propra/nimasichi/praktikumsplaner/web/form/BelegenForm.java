package de.hhu.propra.nimasichi.praktikumsplaner.web.form;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.zeitslot.Zeitslot;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.ZeitslotRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.github.GitHubService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig.UbungswocheConfigService;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("PMD.LawOfDemeter")
public class BelegenForm {

  protected final transient GitHubService ghService;
  protected final transient String login;
  protected final transient long zeitslotId;
  protected final transient ZeitslotRepo zsRepo;
  protected transient Zeitslot zeitslot;
  protected transient boolean valid;
  protected final transient List<String> alerts;
  private final transient UbungswocheConfigService ubwoService;

  protected BelegenForm(final GitHubService ghService,
                        final String login,
                        final long zeitslotId,
                        final ZeitslotRepo zeitslotRepo,
                        final UbungswocheConfigService ubwoService) {
    this.ghService = ghService;
    this.login = login;
    this.zeitslotId = zeitslotId;
    this.zsRepo = zeitslotRepo;
    this.zeitslot = new Zeitslot();
    this.valid = true;
    this.alerts = new ArrayList<>();
    this.ubwoService = ubwoService;
  }

  public void validateForm() {
    checkHandle();
  }

  private void checkHandle() {
    if (!ghService.doesUserExist(login)) {
      alerts.add("Der Github Handle " + login + " ist ungültig");
      valid = false;
    }
  }

  public final boolean isValid() {
    return valid;
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
        valid = false;
      }
    } else {
      alerts.add("Ein Fehler ist aufgetreten: 400");
      valid = false;
    }
  }

}
