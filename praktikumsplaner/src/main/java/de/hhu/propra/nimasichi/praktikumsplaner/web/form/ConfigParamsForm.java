package de.hhu.propra.nimasichi.praktikumsplaner.web.form;

import lombok.Value;

@Value
@SuppressWarnings({"PMD.DefaultPackage", "PMD.CommentDefaultAccessModifier"})
public class ConfigParamsForm {

	String name;
	int modus;
	String anStartdatum;
	String anStartzeit;
	String anSchlussdatum;
	String anSchlusszeit;
	int minPersonen;
	int maxPersonen;
}
