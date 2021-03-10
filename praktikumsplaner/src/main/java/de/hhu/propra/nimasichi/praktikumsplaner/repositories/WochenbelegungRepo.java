package de.hhu.propra.nimasichi.praktikumsplaner.repositories;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.Wochenbelegung;
import org.springframework.data.repository.CrudRepository;

public interface WochenbelegungRepo extends CrudRepository<Wochenbelegung, Long> {
}
