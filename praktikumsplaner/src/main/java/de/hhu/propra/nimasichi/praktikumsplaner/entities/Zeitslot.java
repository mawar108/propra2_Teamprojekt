package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Deprecated
@SuppressWarnings({"PMD.DefaultPackage", "PMD.CommentDefaultAccessModifier"})
public class Zeitslot {
  LocalDateTime ubungsAnfang;
  List<String> tutoren;

  public String tutorenAuflistung() {
    return String.join(", ", tutoren);
  }
}
