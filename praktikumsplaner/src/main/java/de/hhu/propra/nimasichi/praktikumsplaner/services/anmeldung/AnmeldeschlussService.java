package de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig.UbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.zeitslot.Zeitslot;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.ZeitslotRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.github.GitHubService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.NumericConstants.SCHEDULED_RATE;

@Service
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.UnusedPrivateMethod"})
public class AnmeldeschlussService {

  private final transient UbungswocheConfigRepo ubwoRepo;
  private final transient ZeitslotRepo zsRepo;
  private final transient GitHubService ghService;

  public AnmeldeschlussService(final UbungswocheConfigRepo ubwoRepo,
                               final ZeitslotRepo zsRepo,
                               final GitHubService ghService) {
    this.ubwoRepo = ubwoRepo;
    this.zsRepo = zsRepo;
    this.ghService = ghService;
  }

  @Scheduled(fixedRate = SCHEDULED_RATE)
  private void checkAnmeldeschluss() {
    if (ghService.isReady()) {
      final Set<UbungswocheConfig> configs = ubwoRepo.findAll()
              .stream()
              .filter(x -> !x.isReposErstellt())
              .filter(UbungswocheConfig::anmeldeschlussAbgelaufen)
              .collect(Collectors.toSet());

      configs.forEach(c -> c.setReposErstellt(true));
      ubwoRepo.saveAll(configs);

      final Set<Long> configIds = configs.stream()
              .map(UbungswocheConfig::getId)
              .collect(Collectors.toSet());

      final Set<Zeitslot> zeitslots = configIds.stream()
              .map(zsRepo::findZeitslotsByUbungswocheConfigId)
              .flatMap(List::stream)
              .collect(Collectors.toSet());

      zeitslots.forEach(Zeitslot::gruppenErstellen);
      zeitslots.forEach(z -> ghService.createRepositories(z.getGruppenDto()));

    }

  }

}
