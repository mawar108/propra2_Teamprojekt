package de.hhu.propra.nimasichi.praktikumsplaner.web.form;

import de.hhu.propra.nimasichi.praktikumsplaner.web.validation.annotation.ZeitslotId;
import lombok.Data;

@Data
@SuppressWarnings({
    "PMD.ShortVariable"
})
public class ZeitslotBelegenForm {

  @ZeitslotId
  private final int id;

}
