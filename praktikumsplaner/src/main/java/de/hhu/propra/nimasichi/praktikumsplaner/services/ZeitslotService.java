package de.hhu.propra.nimasichi.praktikumsplaner.services;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.wochenbelegung.Zeitslot;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.WochenbelegungRepo;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Service
@SuppressWarnings({
    "PMD.LawOfDemeter"
})
public class ZeitslotService {

  private final transient WochenbelegungRepo wbRepo; // zu langer Name

  public ZeitslotService(final WochenbelegungRepo wbRepo) {
    this.wbRepo = wbRepo;
  }

  public List<Zeitslot> getFreieZeitslotsSorted() {
    final var zeitslots = getZeitslotsFromRepo();
    return sortZeitslots(zeitslots);
  }

  public boolean zeitslotExists(final int zeitslotd) {
    Optional<Zeitslot> maybeZeitslot = wbRepo.findZeitslotById(zeitslotd);
    return maybeZeitslot.isPresent();
  }

  private List<Zeitslot> sortZeitslots(final List<Zeitslot> zeitslots) {
    return zeitslots.stream()
        .sorted(Comparator.comparing(Zeitslot::getUbungsAnfang))
        .collect(Collectors.toList());
  }

  private List<Zeitslot> getZeitslotsFromRepo() {
    final var maybeWobe = wbRepo.findByHighestId();
    List<Zeitslot> zeitslots;

    if (maybeWobe.isEmpty()) {
      zeitslots = new ArrayList<>();
    } else {
      zeitslots = maybeWobe.get().getZeitslots().stream()
          .filter(Zeitslot::minEineFreieGruppe)
          .collect(Collectors.toList());
    }

    return zeitslots;
  }

}
