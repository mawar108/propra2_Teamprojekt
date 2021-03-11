package de.hhu.propra.nimasichi.praktikumsplaner.repositories;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.Wochenbelegung;
import de.hhu.propra.nimasichi.praktikumsplaner.entities.Zeitslot;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WochenbelegungRepo extends CrudRepository<Wochenbelegung, Long> {

  @Query("SELECT * FROM wochenbelegung ORDER BY id DESC LIMIT 1")
  Optional<Wochenbelegung> findByHighestId();

  @Query("SELECT * FROM zeitslot WHERE zeitslot.id = :id")
  Optional<Zeitslot> findZeitslotById(@Param("id") Integer id);

}
