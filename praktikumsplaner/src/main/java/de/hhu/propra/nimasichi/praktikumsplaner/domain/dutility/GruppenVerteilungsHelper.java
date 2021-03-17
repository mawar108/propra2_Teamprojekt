package de.hhu.propra.nimasichi.praktikumsplaner.domain.dutility;

import com.google.common.collect.Lists;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.annotations.Utility;

import java.util.Collections;
import java.util.List;

@Utility
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public final class GruppenVerteilungsHelper {

  private GruppenVerteilungsHelper() { }

  public static <T> List<List<T>> partition(final List<T> list,
                                            final int min,
                                            final int max) {
    List<List<T>> partition;

    Collections.shuffle(list);

    int index = 0;
    do {
      partition = Lists.partition(list, min + index);
      index++;
    } while (!isValid(partition, min, max) && min + index <= max);
    return partition;
  }

  private static <T> boolean isValid(final List<List<T>> partition,
                                     final int min,
                                     final int max) {
    boolean valid = true;

    for (final var list : partition) {
      if (list.size() < min || list.size() > max) {
        valid = false;
        break;
      }
    }

    return valid;
  }
}
