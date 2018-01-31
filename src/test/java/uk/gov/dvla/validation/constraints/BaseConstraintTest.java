package uk.gov.dvla.validation.constraints;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BaseConstraintTest {

    protected static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    protected <T> List<String> messages(Set<ConstraintViolation<T>> violations) {
        return violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
    }

    protected <T> List<String> paths(Set<ConstraintViolation<T>> violations) {
        return violations.stream().map((violation) -> violation.getPropertyPath().toString()).collect(Collectors.toList());
    }

}
