package de.hhu.propra.nimasichi.praktikumsplaner.web.form;

import de.hhu.propra.nimasichi.praktikumsplaner.repositories.ZeitslotRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.github.GitHubService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig.UbungswocheConfigService;

import java.util.List;
import java.util.Set;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.NumericConstants.MIN_GRUPPEN_SIZE;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.NumericConstants.MODUS_GRUPPE;

@SuppressWarnings({
    "PMD.LawOfDemeter",
    "PMD.LongVariable"
})
public class GruppenanmeldungForm extends BelegenForm {

  private final transient List<String> mitglieder; // GitHub-Handles
  private final transient String gruppenName;
  private final transient int maxSize;

  public GruppenanmeldungForm(final GitHubService ghService,
                              final String login,
                              final long zeitslotId,
                              final ZeitslotRepo zeitslotRepo,
                              final UbungswocheConfigService ubwoService,
                              final List<String> mitglieder,
                              final String gruppenName,
                              final int maxSize) {
    super(ghService, login, zeitslotId, zeitslotRepo, ubwoService);
    this.mitglieder = mitglieder;
    this.gruppenName = gruppenName;
    this.maxSize = maxSize;
  }

  @Override
  public void validateForm() {
    super.validateForm();
    checkHandles();
    checkSize(maxSize);
    checkName();
    checkDuplicateMembers();
    checkCreatorIsMember();
    checkRaceCondition();
    checkConfig(MODUS_GRUPPE);
    mitglieder.forEach(this::checkAlreadyInGroup);
  }

  private void checkDuplicateMembers() {
    final var distinctMitglieder
        = Set.of(this.mitglieder.toArray());
    if (distinctMitglieder.size() != this.mitglieder.size()) {
      alerts.add("Mindestens ein Mitglied ist doppelt vorhanden.");
      valid = false;
    }
  }

  private void checkCreatorIsMember() {
    if (!mitglieder.contains(login)) {
      alerts.add("Du kannst keine Gruppe erstellen, in der du nicht drin bist.");
      valid = false;
    }
  }

  private void checkName() {
    if (gruppenName.isBlank()) {
      alerts.add("Gruppenname darf nicht leer sein");
      valid = false;
    }
  }

  private void checkSize(final int maxSize) {
    if (mitglieder.size() > maxSize) {
      alerts.add("Es dürfen maximal nur " + maxSize + " Mitglieder teilnehmen");
      valid = false;
    } else if (mitglieder.size() < MIN_GRUPPEN_SIZE) {
      alerts.add("In einer Gruppe müssen mindestens zwei Personen sein");
      valid = false;
    }
  }

  private void checkHandles() {
    for (final var mitglied : mitglieder) {
      if (!ghService.doesUserExist(mitglied)) {
        alerts.add("Der Github Handle " + mitglied + " ist ungültig");
        valid = false;
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
        valid = false;
      }
    }
  }

}
