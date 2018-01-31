package uk.gov.dvla.validation.constraints;

import javax.validation.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Set;

import static org.apache.commons.beanutils.PropertyUtils.getSimpleProperty;

public class ConditionalValidationValidator implements ConstraintValidator<ConditionalValidation, Object> {

    public static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private String dependsOn;
    private String[] revalidate;
    private Class<?>[] withGroups;

    public void initialize(ConditionalValidation annotation) {
        this.dependsOn = annotation.dependsOn();
        this.revalidate = annotation.revalidate();
        this.withGroups = annotation.withGroups();
    }

    @Override
    public boolean isValid(Object bean, ConstraintValidatorContext context) {
        if (bean == null) {
            return true;
        }

        boolean valid = true;
        try {
            Object condition = getSimpleProperty(bean, dependsOn);
            if (condition instanceof Boolean && (Boolean) condition) {
                context.disableDefaultConstraintViolation();

                for (String property : revalidate) {
                    Set<ConstraintViolation<Object>> shallowCheckViolations = validator.validateProperty(bean, property, withGroups);
                    if (!shallowCheckViolations.isEmpty()) {
                        valid = false;
                    }
                    addViolationsToContext(context, shallowCheckViolations, Optional.empty());

                    Object value = getSimpleProperty(bean, property);
                    if (value != null) { // additionally validate value as @Valid annotation is not covered by #validateProperty method
                        Set<ConstraintViolation<Object>> deepCheckViolations = validator.validate(value, withGroups);
                        if (!deepCheckViolations.isEmpty()) {
                            valid = false;
                        }
                        addViolationsToContext(context, deepCheckViolations, Optional.of(property));
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new ValidationException(ex);
        }
        return valid;
    }

    private void addViolationsToContext(ConstraintValidatorContext context, Set<ConstraintViolation<Object>> violations, Optional<String> propertyPrefix) {
        for (ConstraintViolation<Object> violation : violations) {
            context.buildConstraintViolationWithTemplate(violation.getMessageTemplate())
                    .addPropertyNode(propertyNode(propertyPrefix, violation))
                    .addConstraintViolation();
        }
    }

    private <T> String propertyNode(Optional<String> propertyPrefix, ConstraintViolation<T> violation) {
        StringBuilder builder = new StringBuilder();
        // append violation property prefix
        if (propertyPrefix.isPresent()) {
            builder.append(propertyPrefix.get());
        }
        // append violation property path
        String violationPropertyPath = violation.getPropertyPath().toString();
        if (!violationPropertyPath.isEmpty()) {
            if (builder.length() > 0) {
                builder.append(".");
            }
            builder.append(violationPropertyPath);
        }
        return builder.toString();
    }
}
