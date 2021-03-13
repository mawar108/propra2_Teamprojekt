package de.hhu.propra.nimasichi.praktikumsplaner.services;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.wochenbelegung.Zeitslot;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.WochenbelegungRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestplatzeService {

  private final transient WochenbelegungRepo wobeRepo;

  public RestplatzeService(
      final WochenbelegungRepo wobeRepo) {
    this.wobeRepo = wobeRepo;
  }

  public List<Zeitslot> getRestplatze() {
    List<Zeitslot> restplatze;
    final var maybeWochenbelegung
        = wobeRepo.findByHighestId();

    if (maybeWochenbelegung.isPresent()) {
      restplatze = maybeWochenbelegung
          .get()
          .getZeitslotsWithRestplatze();
    } else {
      restplatze = new ArrayList<>();
    }

    return restplatze;
  }
}
