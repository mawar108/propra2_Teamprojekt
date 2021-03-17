package de.hhu.propra.nimasichi.praktikumsplaner.domain.dto;

import lombok.Data;

import java.util.Set;

@Data
public class GruppeDto {

  private final String gruppenName;
  private final Set<String> mitglieder;

}
