package de.hhu.propra.nimasichi.praktikumsplaner.domain.zeitslot;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@SuppressWarnings("PMD.ShortVariable")
class Student {
  @Id
  private Long id;
  private final String githubHandle;

  /* default */ Student(final String githubHandle) {
    this.githubHandle = githubHandle;
  }
}
