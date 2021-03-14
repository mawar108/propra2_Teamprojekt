package de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig.UbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import org.springframework.stereotype.Service;

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

  public UbungswocheConfig getLatestUbungswocheConfig() {
    final var maybeUbConf = ubWoConfRepo.findByHighestId();
    return maybeUbConf.orElse(null);
  }

}
