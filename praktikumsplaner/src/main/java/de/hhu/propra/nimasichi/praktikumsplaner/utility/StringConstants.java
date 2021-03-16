package de.hhu.propra.nimasichi.praktikumsplaner.utility;

import de.hhu.propra.nimasichi.praktikumsplaner.domain.annotations.Utility;

@Utility
@SuppressWarnings({
    "PMD.LongVariable"
})
public final class StringConstants {
  public static final transient String
      PARAMS_MODEL_NAME = "params";
  public static final transient String
      TUTOREN_TERMINE_MODEL_NAME = "tutorTermine";
  public static final transient String
      MITGLIEDER_MODEL_NAME = "mitglieder";
  public static final transient String
      ZEITSLOT_MODEL_NAME = "zeitslot";
  public static final transient String
          ZEITSLOTS_MODEL_NAME = "zeitslots";
  public static final transient String
      ALERTS_MODEL_NAME = "alerts";
  public static final transient String
      GRUPPENNAME_MODEL_NAME = "gruppenname";
  public static final transient String
      RESTPLATZE_MODEL_NAME = "restplatze";

  public static final transient String
      ROLE_USER = "ROLE_USER";
  public static final transient String
      ROLE_TUTOR = "ROLE_TUTOR";
  public static final transient String
      ROLE_ORGA = "ROLE_ORGA";

  private StringConstants() { }
}
