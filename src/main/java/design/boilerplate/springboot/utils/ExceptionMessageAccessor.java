package design.boilerplate.springboot.utils;

import java.util.Locale;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;


@Service
public class ExceptionMessageAccessor {

  private final MessageSource messageSource;

  ExceptionMessageAccessor(@Qualifier("exceptionMessageSource") MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public String getMessage(String key, Object... parameter) {
    return getMessage(null, key, parameter);
  }

  public String getMessage(Locale locale, String key, Object... parameter) {

    if (Objects.isNull(locale)) {
      return messageSource.getMessage(key, parameter, ProjectConstants.PT_BR_LOCALE);
    }

    return messageSource.getMessage(key, parameter, locale);
  }

}
