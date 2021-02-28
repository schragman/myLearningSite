package util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;
import java.util.List;

// Wird nicht benutzt. Nur für Testzwecke

public class TestValidationValidator implements ConstraintValidator<TestValidation, String> {

  private int minValue, maxValue;
  private List<String> ausschlussliste;
  private String message;

  @Override
  public void initialize(final TestValidation pvAnnotation) {

    if (pvAnnotation.min() == 0 || pvAnnotation.max() == 0) {
      throw new IllegalStateException("Es muss mindestens 0 für min und max angegeben werden");
    }

    /*minValue = pvAnnotation.min();
    maxValue = pvAnnotation.max();*/

    if (minValue >= maxValue) {
      throw new IllegalStateException(
          "Falsches Annotationformat min ist größer als max!");
    }
    message = pvAnnotation.message();

  }

  @Override
  public boolean isValid(
      final String testString, final ConstraintValidatorContext pvConstraintValidatorContext) {

    boolean result = true;
    if (testString == null) {
      message = "ist null";
      result = false;
    } else if (testString.length() < minValue) {
      message = "zu klein";
      result = false;
    } else if (testString.length() > maxValue) {
      result = false;
      message = "zu groß";
    }


    return result;
  }

  public String getMessage() {
    return "Hallo";
  }

}
