package de.hhu.propra.nimasichi.praktikumsplaner.web.validation.validator;

import de.hhu.propra.nimasichi.praktikumsplaner.web.validation.annotation.MitgliederCount;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.MITGLIEDER_MODEL_NAME;

public class MitgliederCountValidator
    implements ConstraintValidator<MitgliederCount,
    HttpServletRequest> {

  private int min;

  @Override
  public void initialize(MitgliederCount constraintAnnotation) {
    this.min = constraintAnnotation.min();
  }

  @Override
  public boolean isValid(HttpServletRequest value, ConstraintValidatorContext context) {
    final var mitglieder = value.getParameterMap().get(MITGLIEDER_MODEL_NAME);
    return mitglieder.length >= min;
  }

}
