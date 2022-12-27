package design.boilerplate.springboot.utils;

import java.util.Locale;


public final class ProjectConstants {

  public static final String DEFAULT_ENCODING = "UTF-8";

  public static final String PROJECT_BASE_PACKAGE = "design.boilerplate.springboot";

  public static final Locale PT_BR_LOCALE = new Locale.Builder().setLanguage("pt").setRegion("BR")
      .build();
  public static final Locale EN_LOCALE = new Locale.Builder().setLanguage("en").setRegion("EN")
      .build();

  private ProjectConstants() {

    throw new UnsupportedOperationException();
  }

}
