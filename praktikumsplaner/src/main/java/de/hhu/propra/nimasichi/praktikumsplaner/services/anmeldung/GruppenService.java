package de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.zeitslot.Zeitslot;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.ZeitslotRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings({
    "PMD.LawOfDemeter"
})
public class GruppenService {

  private final transient ZeitslotRepo zsRepo;

  public GruppenService(final ZeitslotRepo zsRepo) {
    this.zsRepo = zsRepo;
  }

  public void saveGruppeToZeitslot(final long zeitslotId,
                                   final List<String> mitglieder) {
    final Optional<Zeitslot> maybeZeitslot = zsRepo.findZeitslotById(zeitslotId);

    if (maybeZeitslot.isPresent()) {
      final Zeitslot zeitslot = maybeZeitslot.get();
      zeitslot.addMitgliederToRandomGroup(mitglieder);
      zsRepo.save(zeitslot);
    }
  }


}
