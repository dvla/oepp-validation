package uk.gov.dvla.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.deleteWhitespace;

/*
 * Validates a Vehicle Registration Mark (VRM)
 */
public class VRMValidator implements ConstraintValidator<VRM, String> {

    private static final String REGEX = "([A-Za-z]{3}[0-9]{1,4})" +
            "|([A-Za-z][0-9]{1,3}[A-Za-z]{3})" +
            "|([A-Za-z]{3}[0-9]{1,3}[A-Za-z])" +
            "|([A-Za-z]{2}[0-9]{2}[A-Za-z]{3})" +
            "|([A-Za-z]{1,3}[0-9]{1,3})" +
            "|([0-9]{1,4}[A-Za-z]{1,3})" +
            "|([A-Za-z]{1,2}[0-9]{1,4})";

    @Override
    public void initialize(VRM annotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || deleteWhitespace(value).matches(REGEX);
    }

}
