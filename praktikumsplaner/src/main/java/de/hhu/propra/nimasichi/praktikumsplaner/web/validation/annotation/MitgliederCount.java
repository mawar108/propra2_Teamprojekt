package de.hhu.propra.nimasichi.praktikumsplaner.web.validation.annotation;

import de.hhu.propra.nimasichi.praktikumsplaner.web.validation.validator.MitgliederCountValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MitgliederCountValidator.class)
@Target({
    ElementType.METHOD,
    ElementType.FIELD,
    ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
public @interface MitgliederCount {
  int min();

  String message()
      default
      "HttpServletRequest$mitglieder$length ist nicht >= min";
  Class<?>[] groups() default { };
  Class<? extends Payload>[] payload() default { };
}
