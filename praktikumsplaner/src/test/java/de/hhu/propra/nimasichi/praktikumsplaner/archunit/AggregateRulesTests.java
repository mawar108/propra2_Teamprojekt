package de.hhu.propra.nimasichi.praktikumsplaner.archunit;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.dependencies.Slice;
import de.hhu.propra.nimasichi.praktikumsplaner.PraktikumsplanerApplication;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static de.hhu.propra.nimasichi.praktikumsplaner.archunit.rules.HaveExactlyOneAggregateRoot.HAVE_EXACTLY_ONE_AGGREGATE_ROOT;

@AnalyzeClasses(packagesOf = PraktikumsplanerApplication.class)
public class AggregateRulesTests {

  private static final DescribedPredicate<? super Slice> ARE_NOT_UTILITY_PACKAGES
        = new DescribedPredicate<>("slice is not in a utitlity package") {
          @Override
          public boolean apply(final Slice slice) {
            final var description = slice.getDescription();
            return !description.contains("dutility")
                    && !description.contains("dto")
                    && !description.contains("annotations");
          }
        };

  @ArchTest
  static final ArchRule oneAggregateRootPerAggregate = slices()
      .matching("..domain.(*)..")
      .that(ARE_NOT_UTILITY_PACKAGES)
      .should(HAVE_EXACTLY_ONE_AGGREGATE_ROOT);

}
