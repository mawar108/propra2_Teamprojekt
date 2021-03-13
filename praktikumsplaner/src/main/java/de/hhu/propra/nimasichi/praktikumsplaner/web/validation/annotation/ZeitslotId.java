package de.hhu.propra.nimasichi.praktikumsplaner.web.validation.annotation;

import de.hhu.propra.nimasichi.praktikumsplaner.web.validation.validator.ZeitslotIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ZeitslotIdValidator.class)
@Target({
    ElementType.METHOD,
    ElementType.FIELD,
    ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZeitslotId {
  String message() default "Dieser Zeitslot existiert nicht!";
  Class<?>[] groups() default { };
  Class<? extends Payload>[] payload() default { };
}
