package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
public class Student {
  @Id
  String githubHandle;
}
