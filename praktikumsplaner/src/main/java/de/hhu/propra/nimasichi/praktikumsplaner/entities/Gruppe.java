package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import lombok.Data;

import java.util.Set;

@Data
public class Gruppe {
  private String gruppenName;
  private String tutorenName;
  private Set<Student> mitglieder;
}
