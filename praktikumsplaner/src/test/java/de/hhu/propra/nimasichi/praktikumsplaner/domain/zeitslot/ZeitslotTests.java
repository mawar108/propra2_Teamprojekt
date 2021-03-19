package de.hhu.propra.nimasichi.praktikumsplaner.domain.zeitslot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ZeitslotTests {

  private Gruppe getGruppeOfSize(int size) {
    Gruppe gruppe = new Gruppe();
    // gruppen im set mussen unterschiedlich sein
    gruppe.setGruppenName(UUID.randomUUID().toString());
    gruppe.setMitglieder(
        Stream
            .iterate(0, x -> x + 1)
            .map(x -> new Student(String.valueOf(x)))
            .limit(size)
            .collect(Collectors.toSet())
    );
    return gruppe;
  }

  @Test
  @DisplayName("Eine leere Gruppe wird erkannt -> true")
  public void mindEineFreieGruppeTest1() {
    Zeitslot zeitslot = new Zeitslot();
    zeitslot.setGruppen(
        Set.of(
            getGruppeOfSize(2),
            getGruppeOfSize(0)
        )
    );

    boolean freieGruppe = zeitslot.minEineFreieGruppe();

    assertThat(freieGruppe).isTrue();
  }

  @Test
  @DisplayName("Keine freie Gruppe wird erkannt -> false")
  public void mindEineFreieGruppeTest2() {
    Zeitslot zeitslot = new Zeitslot();
    zeitslot.setGruppen(
        Set.of(
            getGruppeOfSize(2),
            getGruppeOfSize(3)
        )
    );

    boolean freieGruppe = zeitslot.minEineFreieGruppe();

    assertThat(freieGruppe).isFalse();
  }

  @Test
  @DisplayName("Nicht komplett belegt")
  public void istKomplettBelegtTest1() {
    Zeitslot zeitslot = new Zeitslot();
    zeitslot.setMaxPersonen(5);
    zeitslot.setGruppen(
        Set.of(
            getGruppeOfSize(2),
            getGruppeOfSize(3)
        )
    );

    boolean belegt = zeitslot.istKomplettBelegt();

    assertThat(belegt).isFalse();
  }

  @Test
  @DisplayName("Nur eine Gruppe ist komplett voll -> belegt = false")
  public void istKomplettBelegtTest2() {
    Zeitslot zeitslot = new Zeitslot();
    zeitslot.setMaxPersonen(6);
    zeitslot.setGruppen(
        Set.of(
            getGruppeOfSize(6),
            getGruppeOfSize(3)
        )
    );

    boolean belegt = zeitslot.istKomplettBelegt();

    assertThat(belegt).isFalse();
  }


  @Test
  @DisplayName("Beide Gruppen sind komplett voll -> belegt = true")
  public void istKomplettBelegtTest3() {
    Zeitslot zeitslot = new Zeitslot();
    zeitslot.setMaxPersonen(6);
    zeitslot.setGruppen(
        Set.of(
            getGruppeOfSize(6),
            getGruppeOfSize(6)
        )
    );

    boolean belegt = zeitslot.istKomplettBelegt();

    assertThat(belegt).isTrue();
  }

  @Test
  @DisplayName("Eine leere Gruppe -> true")
  public void hatFreienGruppenplatzTest1() {
    Zeitslot zeitslot = new Zeitslot();
    zeitslot.setGruppen(
        Set.of(
            getGruppeOfSize(0),
            getGruppeOfSize(4)
        )
    );

    boolean frei = zeitslot.hatFreienGruppenplatz();

    assertThat(frei).isTrue();
  }

  @Test
  @DisplayName("Keine leere Gruppe -> false")
  public void hatFreienGruppenplatzTest2() {
    Zeitslot zeitslot = new Zeitslot();
    zeitslot.setGruppen(
        Set.of(
            getGruppeOfSize(4),
            getGruppeOfSize(3)
        )
    );

    boolean frei = zeitslot.hatFreienGruppenplatz();

    assertThat(frei).isFalse();
  }



}
