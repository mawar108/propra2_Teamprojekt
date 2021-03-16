package de.hhu.propra.nimasichi.praktikumsplaner.repositories;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.zeitslot.Zeitslot;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZeitslotRepo extends CrudRepository<Zeitslot, Long> {

  Optional<Zeitslot> findZeitslotById(Long zeitslotId);

  @Query("SELECT * FROM ZEITSLOT WHERE UBUNGSWOCHE_CONFIG = :id")
  List<Zeitslot> findZeitslotsByUbungswocheConfigId(@Param("id") Long configId);

}
