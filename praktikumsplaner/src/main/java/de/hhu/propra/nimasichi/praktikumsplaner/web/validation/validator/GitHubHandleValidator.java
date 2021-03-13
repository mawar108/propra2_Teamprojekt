package de.hhu.propra.nimasichi.praktikumsplaner.web.validation.validator;

import de.hhu.propra.nimasichi.praktikumsplaner.github.GitHubService;
import de.hhu.propra.nimasichi.praktikumsplaner.web.validation.annotation.GitHubHandle;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GitHubHandleValidator
    implements ConstraintValidator<GitHubHandle, String> {

  @Autowired
  private GitHubService ghService;

  @Override
  public void initialize(GitHubHandle constraintAnnotation) { }

  @Override
  public boolean isValid(String value,
                         ConstraintValidatorContext context) {
    return ghService.doesUserExist(value);
  }
}
