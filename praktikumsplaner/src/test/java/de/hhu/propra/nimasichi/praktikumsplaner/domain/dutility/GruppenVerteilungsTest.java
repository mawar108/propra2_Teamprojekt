package de.hhu.propra.nimasichi.praktikumsplaner.domain.dutility;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class GruppenVerteilungsTest {

  private List<String> getListOfSize(int size) {
    return Stream.iterate(0, x -> x+1)
      .limit(size)
      .map(String::valueOf)
      .collect(Collectors.toList());
  }


  @Test
  @DisplayName("Weniger Studenten angemeldet als die mind. Größe -> nur eine Gruppe")
  void partitionTest1() {
    List<String> listOfSize = getListOfSize(2);
    List<List<String>> partition = GruppenVerteilungsHelper.partition(listOfSize, 3, 5);
    assertThat(partition.size()).isEqualTo(1);
    assertThat(partition.get(0).size()).isEqualTo(2);
  }

  @Test
  @DisplayName("Mehr Studenten als die max Größe -> zwei Gruppen a 3 Studenten")
  void partitionTest2() {
    List<String> listOfSize = getListOfSize(6);
    List<List<String>> partition = GruppenVerteilungsHelper.partition(listOfSize, 3, 5);
    assertThat(partition.size()).isEqualTo(2);
    assertThat(partition.get(0).size()).isEqualTo(3);
    assertThat(partition.get(1).size()).isEqualTo(3);
  }

  @Test
  @DisplayName("Entscheidung fällt für zwei kleine Gruppen aus")
  void partitionTest3() {
    List<String> listOfSize = getListOfSize(8);
    List<List<String>> partition = GruppenVerteilungsHelper.partition(listOfSize, 4, 8);
    assertThat(partition.size()).isEqualTo(2);
    assertThat(partition.get(0).size()).isEqualTo(4);
    assertThat(partition.get(1).size()).isEqualTo(4);

  }

  @Test
  @DisplayName("Mehr Studenten als die max Größe -> drei Gruppen a 3 Studenten")
  void partitionTest4() {
    List<String> listOfSize = getListOfSize(9);
    List<List<String>> partition = GruppenVerteilungsHelper.partition(listOfSize, 3, 5);
    assertThat(partition.size()).isEqualTo(3);
    assertThat(partition.get(0).size()).isEqualTo(3);
    assertThat(partition.get(1).size()).isEqualTo(3);
    assertThat(partition.get(2).size()).isEqualTo(3);
  }


  @Test
  @DisplayName("Mehr Studenten als die max Größe -> möglichst kleine Gruppe")
  void partitionTest5() {
    List<String> listOfSize = getListOfSize(7);
    List<List<String>> partition = GruppenVerteilungsHelper.partition(listOfSize, 3, 5);
    assertThat(partition.size()).isEqualTo(2);
    assertThat(partition.get(0).size()).isEqualTo(4);
    assertThat(partition.get(1).size()).isEqualTo(3);
  }




}
