package de.hhu.propra.nimasichi.praktikumsplaner.utility;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.annotations.Utility;

import java.time.LocalDateTime;

@Utility
@SuppressWarnings("PMD.LawOfDemeter")
public final class RepoNameHelper {

  private RepoNameHelper() {
  }

  public static String getRepoName(final String gruppenname, final LocalDateTime ubungsAnfang) {
    return "Praktische-Ubung-"
            + ubungsAnfang.toLocalDate().toString() + "-"
            + ubungsAnfang.toLocalTime().toString() + "-Team-" + gruppenname;
  }

}
