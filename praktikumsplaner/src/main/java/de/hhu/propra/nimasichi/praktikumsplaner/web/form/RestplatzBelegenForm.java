package de.hhu.propra.nimasichi.praktikumsplaner.web.form;

import de.hhu.propra.nimasichi.praktikumsplaner.repositories.ZeitslotRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.github.GitHubService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig.UbungswocheConfigService;

public class RestplatzBelegenForm extends AbstractBelegenForm {

  public RestplatzBelegenForm(final GitHubService ghService,
                              final String login,
                              final long zeitslotId,
                              final ZeitslotRepo zeitslotRepo,
                              final UbungswocheConfigService ubwoService) {
    super(ghService, login, zeitslotId, zeitslotRepo, ubwoService);
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

  @Override
  public void validateForm() {
    super.validateForm();
    checkRestplatze();
  }

}
