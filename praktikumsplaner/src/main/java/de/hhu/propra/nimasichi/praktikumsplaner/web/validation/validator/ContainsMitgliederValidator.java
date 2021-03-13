package de.hhu.propra.nimasichi.praktikumsplaner.web.validation.validator;

import de.hhu.propra.nimasichi.praktikumsplaner.web.validation.annotation.ContainsMitglieder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.MITGLIEDER_MODEL_NAME;

public class ContainsMitgliederValidator
    implements
    ConstraintValidator<ContainsMitglieder,
        HttpServletRequest> {

  @Override
  public void initialize(ContainsMitglieder constraintAnnotation) { }

  @Override
  public boolean isValid(HttpServletRequest value,
                         ConstraintValidatorContext context) {

    final var mitglieder = value.getParameterMap().get(MITGLIEDER_MODEL_NAME);
    return mitglieder != null;
  }
}
