package de.hhu.propra.nimasichi.praktikumsplaner.archunit.rules;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.library.dependencies.Slice;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.annotations.AggregateRoot;

import java.util.List;

import static com.tngtech.archunit.lang.SimpleConditionEvent.satisfied;
import static com.tngtech.archunit.lang.SimpleConditionEvent.violated;
import static java.util.stream.Collectors.toList;

public class HaveExactlyOneAggregateRoot extends ArchCondition<Slice> {

  public static final HaveExactlyOneAggregateRoot
      HAVE_EXACTLY_ONE_AGGREGATE_ROOT =
      new HaveExactlyOneAggregateRoot(
          "have exactly one Aggregate Root");

  HaveExactlyOneAggregateRoot(final String description,
                              final Object... args) {
    super(description, args);
  }

  public void check(final Slice slice,
                    final ConditionEvents events) {
    final var rootNames
        = getAggregateRootNames(slice);
    final var packageName = slice.iterator()
        .next()
        .getPackageName();
    final var numberOfAggregateRoots
        = rootNames.size();

    switch (numberOfAggregateRoots) {
      case 0:
        events.add(violated(slice,
            packageName
                + " has no aggregate root"));
        break;
      case 1:
        events.add(satisfied(slice, "ok!"));
        break;
      default:
        events.add(violated(slice,
            packageName
                + " has more than one aggregate root"));
    }
  }

  private List<String> getAggregateRootNames(final Slice slice) {
    return slice.stream()
        .filter(c -> c.isAnnotatedWith(AggregateRoot.class))
        .map(JavaClass::getName)
        .collect(toList());
  }
}
