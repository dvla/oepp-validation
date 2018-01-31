package uk.gov.dvla.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This annotation validates the Vehicle Registraion Mark (VRM).
 */
@Constraint(validatedBy = VRMValidator.class)
@Documented
@Target({FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
public @interface VRM {

    String message() default "uk.gov.dvla.validation.constraints.VRM.message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
