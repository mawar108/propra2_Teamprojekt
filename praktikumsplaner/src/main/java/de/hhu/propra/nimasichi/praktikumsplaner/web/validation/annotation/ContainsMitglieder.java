package de.hhu.propra.nimasichi.praktikumsplaner.web.validation.annotation;

import de.hhu.propra.nimasichi.praktikumsplaner.web.validation.validator.ContainsMitgliederValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ContainsMitgliederValidator.class)
@Target({
    ElementType.METHOD,
    ElementType.FIELD,
    ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ContainsMitglieder {
  String message()
      default
      "HttpServletRequest enhielt keine Mitglieder!";
  Class<?>[] groups() default { };
  Class<? extends Payload>[] payload() default { };
}
