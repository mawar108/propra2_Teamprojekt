package de.hhu.propra.nimasichi.praktikumsplaner.services.orga;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.zeitslot.Zeitslot;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.ZeitslotRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@SuppressWarnings("PMD.LawOfDemeter")
public class GruppenAndernService {

  private final transient ZeitslotRepo zsRepo;

  public GruppenAndernService(final ZeitslotRepo zsRepo) {
    this.zsRepo = zsRepo;
  }

  public void studentZuGruppeHinzufugen(final long zeitslotId,
                                        final String gruppenName,
                                        final String studentenName) {

    final Optional<Zeitslot> maybeZeitslot = zsRepo.findZeitslotById(zeitslotId);

    if (maybeZeitslot.isPresent()) {
      final Zeitslot zeitslot = maybeZeitslot.get();
      zeitslot.addMitgliedToGruppe(gruppenName, studentenName);
      zsRepo.save(zeitslot);
    }

  }

  public void studentAusGruppeLoschen(final long zeitslotId,
                                      final String gruppenName,
                                      final String studentenName) {

    final Optional<Zeitslot> maybeZeitslot = zsRepo.findZeitslotById(zeitslotId);

    if (maybeZeitslot.isPresent()) {
      final Zeitslot zeitslot = maybeZeitslot.get();
      zeitslot.deleteMitgliedFromGruppe(gruppenName, studentenName);
      zsRepo.save(zeitslot);
    }
  }
}
