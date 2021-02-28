package util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class InternMessages {

  public static String getMessageForLocale(String messageKey, Locale locale) {
    return ResourceBundle.getBundle("learning", locale).getString(messageKey);
  }

  public static String getMessageForLocaleWParam(String messageKey, Locale locale, String param) {
    return MessageFormat.format(ResourceBundle.getBundle("learning", locale).getString(messageKey), param);
  }


}
