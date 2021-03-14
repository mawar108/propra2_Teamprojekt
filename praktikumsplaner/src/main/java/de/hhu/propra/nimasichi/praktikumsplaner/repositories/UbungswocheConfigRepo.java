package de.hhu.propra.nimasichi.praktikumsplaner.repositories;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.ubungswocheconfig.UbungswocheConfig;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UbungswocheConfigRepo extends CrudRepository<UbungswocheConfig, Long> {

  @Query("SELECT * FROM ubungswoche_config ORDER BY id DESC LIMIT 1")
  Optional<UbungswocheConfig> findByHighestId();

}
