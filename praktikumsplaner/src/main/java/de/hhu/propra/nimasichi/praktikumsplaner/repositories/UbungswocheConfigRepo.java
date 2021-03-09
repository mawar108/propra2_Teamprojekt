package de.hhu.propra.nimasichi.praktikumsplaner.repositories;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.PraktischeUbungswocheConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UbungswocheConfigRepo extends CrudRepository<PraktischeUbungswocheConfig, Long> {

  List<PraktischeUbungswocheConfig> findAll();

}
