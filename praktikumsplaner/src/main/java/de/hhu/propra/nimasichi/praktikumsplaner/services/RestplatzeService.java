package de.hhu.propra.nimasichi.praktikumsplaner.services;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.wochenbelegung.Wochenbelegung;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.wochenbelegung.Zeitslot;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.WochenbelegungRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestplatzeService {

  private final transient WochenbelegungRepo wochenbelegungRepo;

  public RestplatzeService(final WochenbelegungRepo wochenbelegungRepo) {
    this.wochenbelegungRepo = wochenbelegungRepo;
  }

  public List<Zeitslot> getRestplatze() {
    List<Zeitslot> restplatze = new ArrayList<>();
    Optional<Wochenbelegung> maybeWochenbelegung = wochenbelegungRepo.findByHighestId();
    if (maybeWochenbelegung.isPresent()) {
      restplatze = maybeWochenbelegung.get().getZeitslotsWithRestplatze();
    }
    return restplatze;
  }
}
