package de.hhu.propra.nimasichi.praktikumsplaner.domain.zeitslot;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@SuppressWarnings("PMD.ShortVariable")
public class AngemeldeterStudent {
  @Id
  private Long id;
  private final String githubHandle;

  /* default */ AngemeldeterStudent(final String githubHandle) {
    this.githubHandle = githubHandle;
  }
}
