package util.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Constraint;
import javax.validation.Payload;


@Documented
@Constraint(validatedBy = {TestValidationValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)

//Wird nicht benutzt. Nur für Beispiel-Doku

public @interface TestValidation {
  /**
   * Bean-Validation: Mindestwert.
   *
   * @return Mindestwert
   */
  int min();

  int max();

  /**
   * Bean-Validation: Zuordnung der Validierungsgruppe.
   *
   * @return Validierungsgruppe
   * @see javax.validation.metadata.ConstraintDescriptor#getGroups()
   */
  @SuppressWarnings("unused") /* 2020-11-11  Wird für Bean-Validation zwingend benötigt!
      Andernfalls wird folgende Ausnahme ausgelöst: "javax.validation.ConstraintDefinitionException:
      Annotation interface de.nordlb.cpc.validation.constraints.DoubleMin has no groups() method" */
      Class<?>[] groups() default {};

  /**
   * Bean-Validation: Fehlermeldung.
   *
   * @return Fehler-Code
   */
  String message() default "Format of Date is wrong or outside the boundaries!";

  /**
   * Bean-Validation: Payload Element.
   *
   * @return Payload
   * @see javax.validation.metadata.ConstraintDescriptor#getPayload()
   */
  @SuppressWarnings("unused") /* 2020-11-11  Bean-Validation zwingend benötigt!
      Andernfalls wird folgende Ausnahme ausgelöst: "javax.validation.ConstraintDefinitionException:
      Annotation interface de.nordlb.cpc.validation.constraints.DoubleMin has no payload() method"
       */
      Class<? extends Payload>[] payload() default {};


}
