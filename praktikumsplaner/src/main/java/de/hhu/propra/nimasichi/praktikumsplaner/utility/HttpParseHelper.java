package de.hhu.propra.nimasichi.praktikumsplaner.utility;

import de.hhu.propra.nimasichi.praktikumsplaner.entities.TutorTermin;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.MITGLIEDER_MODEL_NAME;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.TUTOREN_TERMINE_MODEL_NAME;

public final class HttpParseHelper {
  private HttpParseHelper() { }

  public static List<TutorTermin> parseTutorZeitenFromReq(
      final Map<String, String[]> paramMap) {

    final String[] tutorTermin = paramMap.get(TUTOREN_TERMINE_MODEL_NAME);
    List<TutorTermin> parsedList;

    if (tutorTermin == null) {
      parsedList = new ArrayList<>();
    } else {
      parsedList = Arrays.stream(tutorTermin)
          .map(TutorTermin::fromParseable)
          .collect(Collectors.toList());
    }

    return parsedList;
  }

  public static List<String> parseMitgliederFromReq(
      final Map<String, String[]> paramMap) {

    final String[] mitglieder = paramMap.get(MITGLIEDER_MODEL_NAME);
    List<String> parsedList;

    if (mitglieder == null) {
      parsedList = new ArrayList<>();
    } else {
      parsedList = new ArrayList<>(Arrays.asList(mitglieder));
    }

    return parsedList;
  }
}
