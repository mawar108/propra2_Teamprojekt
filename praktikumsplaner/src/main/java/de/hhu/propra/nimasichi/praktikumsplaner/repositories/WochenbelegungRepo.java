package de.hhu.propra.nimasichi.praktikumsplaner.repositories;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.Wochenbelegung;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WochenbelegungRepo extends CrudRepository<Wochenbelegung, Long> {

	@Query("SELECT * FROM wochenbelegung ORDER BY id DESC LIMIT 1")
	Optional<Wochenbelegung> findByHighestId();
}
