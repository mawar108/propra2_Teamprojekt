package de.hhu.propra.nimasichi.praktikumsplaner.services;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.Wochenbelegung;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.Zeitslot;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.WochenbelegungRepo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ZeitslotService {

	private final transient WochenbelegungRepo wbRepo; // zu langer Name

	public ZeitslotService(WochenbelegungRepo wbRepo) {
		this.wbRepo = wbRepo;
	}

	public Set<Zeitslot> getFreieZeitslots() {
		Optional<Wochenbelegung> maybeWobe = wbRepo.findByHighestId();
		Set<Zeitslot> zeitslots;
		if (maybeWobe.isEmpty()) {
			zeitslots = new HashSet<>();
		} else {
			zeitslots = maybeWobe.get().getZeitslots().stream()
					.filter(Zeitslot::alleGruppenAngemeldet)
					.collect(Collectors.toSet());
		}
		return zeitslots;
	}

}
