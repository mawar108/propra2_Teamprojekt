package de.hhu.propra.nimasichi.praktikumsplaner.web.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

import java.util.List;

@Data
public class MitgliedHinzufugenForm {

  @NotBlank
  private final String mitgliedName;
  @NotBlank
  private final int zeitslotId;
  @NotBlank
  private List<String> gruppenMitglieder;

  public MitgliedHinzufugenForm(String mitgliedName, int zeitslotId) {
    this.mitgliedName = mitgliedName;
    this.zeitslotId = zeitslotId;
  }

}
