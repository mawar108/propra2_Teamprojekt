package de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.zeitslot.Zeitslot;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.ZeitslotRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig.UbungswocheConfigService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings({
    "PMD.LawOfDemeter"
})
public class ZeitslotService {

  private final transient ZeitslotRepo zsRepo;
  private final transient UbungswocheConfigService configService;

  public ZeitslotService(final ZeitslotRepo zsRepo,
                         final UbungswocheConfigService configService) {
    this.zsRepo = zsRepo;
    this.configService = configService;
  }

  public List<Zeitslot> getAktuelleZeitslots() {
    List<Zeitslot> zeitslots;

    final var maybeAktConfig
        = configService.getAktuelleUbungswocheConfig();

    if (maybeAktConfig.isEmpty()) {
      zeitslots = new ArrayList<>();
    } else {
      zeitslots = zsRepo.findZeitslotsByUbungswocheConfigId(
          maybeAktConfig.get().getId());
    }
    return zeitslots;
  }

  public List<Zeitslot> getAktuelleFreieZeitslotsSorted() {
    var zeitslots = getAktuelleZeitslots();
    zeitslots = getFreieZeitslots(zeitslots);
    return sortZeitslots(zeitslots);
  }

  public boolean zeitslotExists(final Long zeitslotd) {
    final var maybeZeitslot = zsRepo.findZeitslotById(zeitslotd);
    return maybeZeitslot.isPresent();
  }

  private List<Zeitslot> sortZeitslots(final List<Zeitslot> zeitslots) {
    return zeitslots.stream()
        .sorted(Comparator.comparing(Zeitslot::getUbungsAnfang))
        .collect(Collectors.toList());
  }

  private List<Zeitslot> getFreieZeitslots(final List<Zeitslot> zeitslots) {
    return zeitslots.stream()
        .filter(Zeitslot::minEineFreieGruppe)
        .collect(Collectors.toList());
  }

}
