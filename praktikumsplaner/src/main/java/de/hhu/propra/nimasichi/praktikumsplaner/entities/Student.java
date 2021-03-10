package de.hhu.propra.nimasichi.praktikumsplaner.entities;

import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
@SuppressWarnings({
    "PMD.CommentDefaultAccessModifier",
    "PMD.DefaultPackage"})
public class Student {
  @Id
  String githubHandle;
}
