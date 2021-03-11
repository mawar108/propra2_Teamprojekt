package de.hhu.propra.nimasichi.praktikumsplaner.services;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UbungswocheConfigService {

  private final transient UbungswocheConfigRepo ubWoConfRepo;

  public UbungswocheConfigService(UbungswocheConfigRepo ubWoConfRepo) {
    this.ubWoConfRepo = ubWoConfRepo;
  }

  public PraktischeUbungswocheConfig getLatestUbungswocheConfig() {
    Optional<PraktischeUbungswocheConfig> maybeUbConf = ubWoConfRepo.findByHighestId();

    return maybeUbConf.orElse(null);
  }

}
