package de.hhu.propra.nimasichi.praktikumsplaner.web.form;

import de.hhu.propra.nimasichi.praktikumsplaner.web.validation.annotation.GitHubHandle;
import de.hhu.propra.nimasichi.praktikumsplaner.web.validation.annotation.ZeitslotId;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class MitgliedHinzufugenForm {

  private final HttpServletRequest req;

  @NotBlank
  @Size(
      min = 1,
      max = 39,
      message = "Ein GitHub-Name muss zwischen 1 und 39 Zeichen enthalten."
  )
  @GitHubHandle
  private final String mitgliedName; // GitHub-Handle

  @ZeitslotId
  private final int zeitslotId;

}
