package de.hhu.propra.nimasichi.praktikumsplaner.domain.dto;

import lombok.Value;

import java.util.Set;

@Value
public class GruppeDto {

  private String gruppenName;
  private Set<String> mitglieder;
  private String tutorName;

}
