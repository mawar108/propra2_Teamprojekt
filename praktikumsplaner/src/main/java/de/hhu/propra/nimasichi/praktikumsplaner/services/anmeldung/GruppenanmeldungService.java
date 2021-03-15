package de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung;

import de.hhu.propra.nimasichi.praktikumsplaner.repositories.WochenbelegungRepo;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@SuppressWarnings({
    "PMD.LawOfDemeter"
})
public class GruppenanmeldungService {

  private final transient WochenbelegungRepo wobeRepo;

  public GruppenanmeldungService(
      final WochenbelegungRepo wobeRepo) {
    this.wobeRepo = wobeRepo;
  }

  public void saveGruppeToZeitslot(final Long zeitslotId,
                                   final Set<String> mitglieder) {
    final var maybeCurrent = wobeRepo.findByHighestId();
    if (maybeCurrent.isPresent()) {
      final var current = maybeCurrent.get();
      current.addGruppeToZeitslot(zeitslotId, mitglieder);
    }
  }

}
