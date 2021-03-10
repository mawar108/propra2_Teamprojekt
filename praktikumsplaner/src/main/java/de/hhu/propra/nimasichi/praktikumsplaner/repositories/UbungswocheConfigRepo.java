package de.hhu.propra.nimasichi.praktikumsplaner.repositories;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UbungswocheConfigRepo extends CrudRepository<PraktischeUbungswocheConfig, Long> {

  List<PraktischeUbungswocheConfig> findAll();

  @Query("SELECT * FROM praktische_ubungswoche_config ORDER BY id DESC LIMIT 1")
  Optional<PraktischeUbungswocheConfig> findByHighestId();
}
