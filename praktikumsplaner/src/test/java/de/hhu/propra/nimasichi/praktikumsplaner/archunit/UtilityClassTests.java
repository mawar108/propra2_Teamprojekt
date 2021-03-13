package de.hhu.propra.nimasichi.praktikumsplaner.archunit;

import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import de.hhu.propra.nimasichi.praktikumsplaner.PraktikumsplanerApplication;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.annotations.Utility;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

@AnalyzeClasses(
    packagesOf = PraktikumsplanerApplication.class,
    importOptions = {ImportOption.DoNotIncludeTests.class})
public final class UtilityClassTests {

  @ArchTest
  static final ArchRule
      utilityClassesShouldHavePrivateConstructors =
      classes()
          .that()
          .areAnnotatedWith(Utility.class)
          .should()
          .haveOnlyPrivateConstructors();

  @ArchTest
  static final ArchRule
      utilityClassesShouldOnlyHaveStaticMethods =
      methods()
        .that()
        .areDeclaredInClassesThat()
        .areAnnotatedWith(Utility.class)
        .should()
        .beStatic();

  @ArchTest
  static final ArchRule
      utilityClassesShouldBeFinal =
      classes()
        .that()
        .areAnnotatedWith(Utility.class)
        .should()
        .haveModifier(JavaModifier.FINAL);

  private UtilityClassTests() { }

}
