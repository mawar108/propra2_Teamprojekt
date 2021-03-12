package de.hhu.propra.nimasichi.praktikumsplaner.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import de.hhu.propra.nimasichi.praktikumsplaner.PraktikumsplanerApplication;

import static com.tngtech.archunit.base.DescribedPredicate.alwaysTrue;
import static com.tngtech.archunit.core.domain.properties.HasName.Predicates.nameMatching;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(
    packagesOf = PraktikumsplanerApplication.class,
    importOptions = { ImportOption.DoNotIncludeTests.class })
public class ArchSchichtTests {

  @ArchTest
  static final ArchRule schicht = layeredArchitecture()
          .ignoreDependency(
              nameMatching(".*\\.PraktikumsplanerApplication"),
              alwaysTrue())
          .layer("Web").definedBy("..web..")
          .layer("Services").definedBy("..services..")
          .layer("Repositories").definedBy("..repositories..")
          .layer("Utility").definedBy("..utility..")
          .layer("Domain").definedBy("..domain..")
          .whereLayer("Web").mayNotBeAccessedByAnyLayer()
          .whereLayer("Services").mayOnlyBeAccessedByLayers("Web")
          .whereLayer("Repositories").mayOnlyBeAccessedByLayers(
               "Web",
                          "Services",
                          "Repositories")
          .whereLayer("Utility").mayOnlyBeAccessedByLayers("Services", "Web")
          .whereLayer("Domain").mayOnlyBeAccessedByLayers(
              "Repositories",
                          "Services",
                          "Web",
                          "Utility");

  private ArchSchichtTests() { }

}
