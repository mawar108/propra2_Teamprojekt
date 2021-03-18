package de.hhu.propra.nimasichi.praktikumsplaner.web.form;

import de.hhu.propra.nimasichi.praktikumsplaner.repositories.ZeitslotRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.github.GitHubService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig.UbungswocheConfigService;

@SuppressWarnings("PMD.LawOfDemeter")
public class IndividualBelegenForm extends BelegenForm {

  public IndividualBelegenForm(final GitHubService ghService,
                               final String login,
                               final long zeitslotId,
                               final ZeitslotRepo zeitslotRepo,
                               final UbungswocheConfigService ubwoService) {
    super(ghService, login, zeitslotId, zeitslotRepo, ubwoService);
  }

  private void checkRestplatze() {
    if (zeitslot != null && zeitslot.istKomplettBelegt()) {
      alerts.add("Zeitslot komplett belegt!");
      valid = false;
    }
  }

  @Override
  public void validateForm() {
    super.validateForm();
    checkRestplatze();
    checkAlreadyInGroup(login);
  }

}
