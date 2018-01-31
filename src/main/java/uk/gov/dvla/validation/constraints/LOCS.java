package uk.gov.dvla.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public interface LOCS {

    /**
     * Validates the case number used in LOCS system.
     */
    @Constraint(validatedBy = LOCSCaseNumberValidator.class)
    @Documented
    @Target({FIELD, METHOD, PARAMETER})
    @Retention(RUNTIME)
    @interface CaseNumber {

        String message() default "uk.gov.dvla.validation.constraints.LOCS.CaseNumber.message";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

    }

}
