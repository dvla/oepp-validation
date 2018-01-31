package uk.gov.dvla.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
 * Validates a case number used in LOCS system.
 */
public class LOCSCaseNumberValidator implements ConstraintValidator<LOCS.CaseNumber, Long> {

    private static final String REGEX = "[\\d]{8}";

    @Override
    public void initialize(LOCS.CaseNumber annotation) {}

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return value == null || Long.toString(value).matches(REGEX);
    }

}
