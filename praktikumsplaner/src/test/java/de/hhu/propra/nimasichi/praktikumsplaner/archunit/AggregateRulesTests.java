package de.hhu.propra.nimasichi.praktikumsplaner.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static de.hhu.propra.nimasichi.praktikumsplaner.archunit.rules.HaveExactlyOneAggregateRoot.HAVE_EXACTLY_ONE_AGGREGATE_ROOT;
import static org.springframework.util.StringUtils.capitalize;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.dependencies.Slice;
import de.hhu.propra.nimasichi.praktikumsplaner.PraktikumsplanerApplication;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.annotations.AggregateRoot;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.annotations.Utility;

import java.util.Set;


@AnalyzeClasses(packagesOf = PraktikumsplanerApplication.class)
public class AggregateRulesTests {


	private static DescribedPredicate<? super JavaMethod> ARE_NOT_SETTERS
			= new DescribedPredicate<JavaMethod>("method is not a setter") {
		@Override
		public boolean apply(JavaMethod method) {
			boolean isSetter = false;

			JavaClass javaClass = method.getOwner();
			Set<JavaField> fields = javaClass.getAllFields();

			for (JavaField f : fields) {
				try {
					var methodName = "set" + capitalize(f.getName());
					var parameterClass = Class.forName(f.getRawType().getFullName());
					isSetter = javaClass.tryGetMethod(methodName, parameterClass).isPresent();
					if (isSetter) {
						break;
					}
				} catch (Exception ignored) { }
			}
			return isSetter;
		}
	};

	private static DescribedPredicate<? super JavaMethod> ARE_NOT_GETTERS
			= new DescribedPredicate<JavaMethod>("method is not a getter") {
		@Override
		public boolean apply(JavaMethod method) {
			boolean isGetter = false;

			JavaClass javaClass = method.getOwner();
			Set<JavaField> fields = javaClass.getAllFields();

			for (JavaField f : fields) {
				try {
					var methodName = "get" + capitalize(f.getName());
					var parameterClass = Class.forName(f.getRawType().getFullName());
					isGetter = javaClass.tryGetMethod(methodName, parameterClass).isPresent();
					if (isGetter) {
						break;
					}
				} catch (Exception ignored) { }
			}
			return isGetter;
		}
	};

	private static final DescribedPredicate<? super JavaMethod> ARE_NOT_CONSTRUCTORS
			= new DescribedPredicate<JavaMethod>("method is not a constructor") {
		@Override
		public boolean apply(JavaMethod method) {
			String className = method.getOwner().getSimpleName();
			var methodName = method.getName();
			return !className.equals(methodName);
		}
	};


	@ArchTest
	static final ArchRule onlyAggregateRootsArePublic = methods()
			.that()
			.areDeclaredInClassesThat()
			.resideInAPackage("..domain..")
			.and()
			.areNotAnnotatedWith(AggregateRoot.class)
			.and()
			.areNotAnnotatedWith(Utility.class)
			.and(ARE_NOT_CONSTRUCTORS) // jdbc braucht public setter, getter und constructors
			.and(ARE_NOT_SETTERS)
			.and(ARE_NOT_GETTERS)
			.should()
			.notBePublic()
			.because("the implementation of an aggregate " +
					"should be hidden");



	private static final DescribedPredicate<? super Slice> ARE_NOT_UTILITY_PACKAGES
			= new DescribedPredicate<Slice>("slice is not in a utitlity package") {
		@Override
		public boolean apply(Slice slice) {
			String description = slice.getDescription();
			if (description.contains("dutility") || description.contains("annotations")) {
				System.out.println(description);
				return false;
			}
			return true;
		}
	};

	@ArchTest
	static final ArchRule oneAggregateRootPerAggregate = slices()
			.matching("..domain.(*)..")
			.that(ARE_NOT_UTILITY_PACKAGES)
			.should(HAVE_EXACTLY_ONE_AGGREGATE_ROOT);

}
