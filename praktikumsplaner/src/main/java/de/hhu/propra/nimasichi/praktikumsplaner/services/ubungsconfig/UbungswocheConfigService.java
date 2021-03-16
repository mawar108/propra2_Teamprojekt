package de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig.UbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO
@Service
@SuppressWarnings({
    "PMD.LawOfDemeter"
})
public class UbungswocheConfigService {


  private final transient UbungswocheConfigRepo ubWoConfRepo;

  public UbungswocheConfigService(
      final UbungswocheConfigRepo ubWoConfRepo) {
    this.ubWoConfRepo = ubWoConfRepo;
  }

  public Optional<UbungswocheConfig> getAktuelleUbungswocheConfig() {
    Optional<UbungswocheConfig> maybeAktConfig;

    final var configs = ubWoConfRepo.findAll();

    final var aktulleConfigs = configs.stream()
        .filter(UbungswocheConfig::isAktuell)
        .collect(Collectors.toList());

    if (aktulleConfigs.isEmpty()) {
      maybeAktConfig = Optional.empty();
    } else {
      final List<UbungswocheConfig> sortedByDate =
          aktulleConfigs.stream()
              .sorted(Comparator.comparing(UbungswocheConfig::getAnmeldestart))
              .collect(Collectors.toList());
      maybeAktConfig = Optional.of(sortedByDate.get(0));
    }

    return maybeAktConfig;
  }
}
