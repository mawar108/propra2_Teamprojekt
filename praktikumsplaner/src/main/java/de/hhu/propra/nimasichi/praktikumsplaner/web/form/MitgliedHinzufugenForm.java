package de.hhu.propra.nimasichi.praktikumsplaner.web.form;

import de.hhu.propra.nimasichi.praktikumsplaner.unit.github.GitHubService;
import de.hhu.propra.nimasichi.praktikumsplaner.web.validation.annotation.GitHubHandle;
import de.hhu.propra.nimasichi.praktikumsplaner.web.validation.annotation.ZeitslotId;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class MitgliedHinzufugenForm {

  private final int zeitslotId;
  private final List<String> mitglieder; //github Handles
  private final List<String> alerts = new ArrayList<>();
  private final String gruppenName;

  private boolean isValid = true;

  public void validateForm(GitHubService gitHubService,  int maxSize) {
    checkHandles(gitHubService);
    checkSize(maxSize);
    checkName();
  }

  private void checkName() {
    if (gruppenName.isBlank()) {
      alerts.add("Gruppenname darf nicht leer sein");
      isValid = false;
    }
  }

  private void checkSize(int maxSize) {
    if (mitglieder.size() > maxSize) {
      alerts.add("Es dürfen maximal nur " + maxSize + " Mitglieder teilnehmen");
      isValid = false;
    } else if (mitglieder.size() <= 1) {
      alerts.add("In einer Gruppe müssen mindestens zwei Personen sein");
      isValid = false;
    }
  }

  private void checkHandles(GitHubService gitHubService) {
    for (String mitglied : mitglieder) {
      if (!gitHubService.doesUserExist(mitglied)) {
        alerts.add("Der Github Handle " + mitglied + " ist ungültig");
        isValid = false;
      }
    }
  }

}
