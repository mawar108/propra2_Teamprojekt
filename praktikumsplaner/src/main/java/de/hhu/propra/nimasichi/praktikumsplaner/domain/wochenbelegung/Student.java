package de.hhu.propra.nimasichi.praktikumsplaner.domain.wochenbelegung;

import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
class Student {
  @Id
  private String githubHandle;
}
