package de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.zeitslot.Zeitslot;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings({
    "PMD.LawOfDemeter"
})
public class RestplatzeService {


  private final transient ZeitslotService zsService;

  public RestplatzeService(final ZeitslotService zsService) {
    this.zsService = zsService;
  }

  public List<Zeitslot> getAktuelleRestplatze() {
    final var aktuelleZeitslots = zsService.getAktuelleZeitslots();

    return aktuelleZeitslots.stream().filter(Zeitslot::hatRestplatze).collect(Collectors.toList());
  }
}
