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

	public List<Zeitslot> getFreieZeitslotsSorted() {
		var zeitslots = getZeitslotsFromRepo();
		return sortZeitslots(zeitslots);
	}

	private List<Zeitslot> sortZeitslots(final List<Zeitslot> zeitslots) {
		return zeitslots.stream()
				.sorted(Comparator.comparing(Zeitslot::getUbungsAnfang))
				.collect(Collectors.toList());
	}

	private List<Zeitslot> getZeitslotsFromRepo() {
		Optional<Wochenbelegung> maybeWobe = wbRepo.findByHighestId();

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
