package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@SuppressWarnings({"PMD.DefaultPackage", "PMD.CommentDefaultAccessModifier"})
public class Zeitslot {
  private LocalDateTime ubungsAnfang;
  private Set<Gruppe> gruppen;
  private int minPersonen;
  private int maxPersonen;
}
