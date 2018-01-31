package uk.gov.dvla.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Comparator;

/**
 * Checks whether selected properties (see: {@link #properties()} have the same value. The properties are compared using
 * the comparator that is provided {@link #comparator()}, if no comparator is provided then {@link PropertiesMatchValidator.DefaultComparator} is used.
 * <p>
 * By default constraint violation is recorded against class but it is also possible
 * to record violation against specified properties (see: {@link #violationOn()}).
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PropertiesMatchValidator.class)
@Documented
public @interface PropertiesMatch {

    String[] properties();

    String[] violationOn() default {};

    Class<? extends Comparator> comparator() default PropertiesMatchValidator.DefaultComparator.class;

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
