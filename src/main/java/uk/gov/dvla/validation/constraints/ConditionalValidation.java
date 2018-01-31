package uk.gov.dvla.validation.constraints;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Re-validates selected properties (see: {@link #revalidate()}) using specified groups (see {@link #withGroups()})
 * when dependent property (see: {@link #dependsOn()}) is resolved to true.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConditionalValidationValidator.class)
@Documented
public @interface ConditionalValidation {

    String dependsOn();

    String[] revalidate();

    Class<?>[] withGroups();

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
