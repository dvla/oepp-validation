package uk.gov.dvla.validation.constraints;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PropertiesMatchTest extends BaseConstraintTest {

    @Test
    public void validWhenAllPropertiesAreNull() {
        BeanValidatedAgainstClassDefaultComparator bean = new BeanValidatedAgainstClassDefaultComparator(null, null);

        Set<ConstraintViolation<BeanValidatedAgainstClassDefaultComparator>> violations = validator.validate(bean);
        assertThat(violations.isEmpty(), is(true));
    }

    @Test
    public void validWhenAllPropertiesAreNullUsingStringComparator() {
        BeanValidatedAgainstClassStringComparator bean = new BeanValidatedAgainstClassStringComparator(null, null);

        Set<ConstraintViolation<BeanValidatedAgainstClassStringComparator>> violations = validator.validate(bean);
        assertThat(violations.isEmpty(), is(true));
    }

    @Test
    public void validWhenAllPropertiesAreTheSame() {
        BeanValidatedAgainstClassDefaultComparator bean = new BeanValidatedAgainstClassDefaultComparator("foo", "foo");

        Set<ConstraintViolation<BeanValidatedAgainstClassDefaultComparator>> violations = validator.validate(bean);
        assertThat(violations.isEmpty(), is(true));
    }

    @Test
    public void validWhenAllPropertiesAreTheSameUsingStringComparator() {
        BeanValidatedAgainstClassStringComparator bean = new BeanValidatedAgainstClassStringComparator("foo", "foo");

        Set<ConstraintViolation<BeanValidatedAgainstClassStringComparator>> violations = validator.validate(bean);
        assertThat(violations.isEmpty(), is(true));
    }

    @Test
    public void validWhenAllPropertiesAreTheSameIrrelevantOfCaseUsingStringComparator() {
        BeanValidatedAgainstClassStringComparator bean = new BeanValidatedAgainstClassStringComparator("foo", "FOO");

        Set<ConstraintViolation<BeanValidatedAgainstClassStringComparator>> violations = validator.validate(bean);
        assertThat(violations.isEmpty(), is(true));
    }

    @Test
    public void invalidWhenPropertiesAreNotEqual_recordViolationAgainstClass() {
        BeanValidatedAgainstClassDefaultComparator bean = new BeanValidatedAgainstClassDefaultComparator("foo", "bar");

        Set<ConstraintViolation<BeanValidatedAgainstClassDefaultComparator>> violations = validator.validate(bean);
        assertThat(violations.size(), is(1));
        assertThat(messages(violations), containsInAnyOrder("validation.mismatch"));
        assertThat(paths(violations), containsInAnyOrder(""));
    }

    @Test
    public void invalidWhenPropertiesAreNotEqual_recordViolationAgainstProperty() {
        BeanValidatedAgainstPropertyDefaultComparator bean = new BeanValidatedAgainstPropertyDefaultComparator("foo", "bar");

        Set<ConstraintViolation<BeanValidatedAgainstPropertyDefaultComparator>> violations = validator.validate(bean);
        assertThat(violations.size(), is(1));
        assertThat(messages(violations), containsInAnyOrder("validation.mismatch"));
        assertThat(paths(violations), containsInAnyOrder("bar"));
    }

    @PropertiesMatch(properties = {"foo", "bar"}, message = "validation.mismatch")
    public static class BeanValidatedAgainstClassDefaultComparator extends Bean {
        public BeanValidatedAgainstClassDefaultComparator(String foo, String bar) {
            super(foo, bar);
        }
    }

    @PropertiesMatch(properties = {"foo", "bar"}, comparator = PropertiesMatchValidator.CaseInsensitiveStringComparator.class, message = "validation.mismatch")
    public static class BeanValidatedAgainstClassStringComparator extends Bean {
        public BeanValidatedAgainstClassStringComparator(String foo, String bar) {
            super(foo, bar);
        }
    }

    @PropertiesMatch(properties = {"foo", "bar"}, violationOn = "bar", message = "validation.mismatch")
    public static class BeanValidatedAgainstPropertyDefaultComparator extends Bean {
        public BeanValidatedAgainstPropertyDefaultComparator(String foo, String bar) {
            super(foo, bar);
        }
    }

    public static class Bean {
        public Bean(String foo, String bar) {
            this.foo = foo;
            this.bar = bar;
        }

        private String foo;
        private String bar;

        public String getFoo() {
            return foo;
        }

        public String getBar() {
            return bar;
        }
    }

}
