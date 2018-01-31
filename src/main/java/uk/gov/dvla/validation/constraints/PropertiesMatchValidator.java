package uk.gov.dvla.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Objects;

import static org.apache.commons.beanutils.PropertyUtils.getSimpleProperty;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

public class PropertiesMatchValidator implements ConstraintValidator<PropertiesMatch, Object> {

    private String[] properties;
    private String[] violationOn;
    private Comparator<Object> comparator;

    public void initialize(PropertiesMatch annotation) {
        this.properties = annotation.properties();
        this.violationOn = annotation.violationOn();
        try {
            comparator = annotation.comparator().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new ValidationException("Error creating instance of comparator", ex);
        }
    }

    @Override
    public boolean isValid(Object bean, ConstraintValidatorContext context) {
        if (bean == null) {
            return true;
        }

        boolean valid = true;
        try {
            Object firstValue = null;
            for (String property : properties) {
                Object value = getSimpleProperty(bean, property);
                if (value == null) {
                    continue;
                }

                if (firstValue == null) {
                    firstValue = value;
                    continue;
                }

                if (comparator.compare(value, firstValue) != 0) {
                    valid = false;

                    if (violationOn.length > 0) {
                        context.disableDefaultConstraintViolation();
                        for (String propertyNode : violationOn) {
                            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                                    .addPropertyNode(propertyNode)
                                    .addConstraintViolation();
                        }
                    }

                    break;
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new ValidationException(ex);
        }
        return valid;
    }

    /**
     * A comparator which just uses the default equals method for the objects provided.
     */
    public static class DefaultComparator implements Comparator<Object> {
        @Override
        public int compare(Object firstObject, Object secondObject) {
            return Objects.equals(firstObject, secondObject) ? 0 : -1;
        }
    }

    public static class CaseInsensitiveStringComparator implements Comparator<String> {
        @Override
        public int compare(String firstString, String secondString) {
            return equalsIgnoreCase(firstString, secondString) ? 0 : -1;
        }
    }
}

