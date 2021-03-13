package de.hhu.propra.nimasichi.praktikumsplaner.web.validation.validator;

import de.hhu.propra.nimasichi.praktikumsplaner.repositories.WochenbelegungRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.web.validation.annotation.ZeitslotId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ZeitslotIdValidator implements ConstraintValidator<ZeitslotId, Integer> {

  @Autowired
  private WochenbelegungRepo wobeRepo;

  @Override
  public void initialize(ZeitslotId constraintAnnotation) { }

  @Override
  public boolean isValid(Integer value,
                         ConstraintValidatorContext context) {
    final var maybeZeitslot = wobeRepo.findZeitslotById(value);
    return maybeZeitslot.isPresent();
  }
}
