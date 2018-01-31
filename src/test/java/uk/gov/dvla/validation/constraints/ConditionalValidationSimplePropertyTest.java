package uk.gov.dvla.validation.constraints;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConditionalValidationSimplePropertyTest extends BaseConstraintTest {

    @Test
    public void validWhenConditionEvaluatesToFalse() {
        BeanWithSimpleProperty bean = new BeanWithSimpleProperty(false, null);

        Set<ConstraintViolation<BeanWithSimpleProperty>> violations = validator.validate(bean);
        assertThat(violations.isEmpty(), is(true));
    }

    @Test
    public void validWhenConditionEvaluatesToTrueAndPropertyIsValid() {
        BeanWithSimpleProperty bean = new BeanWithSimpleProperty(false, "value");

        Set<ConstraintViolation<BeanWithSimpleProperty>> violations = validator.validate(bean);
        assertThat(violations.isEmpty(), is(true));
    }

    @Test
    public void invalidWhenConditionEvaluatesToTrueAndPropertyIsInvalid() {
        BeanWithSimpleProperty bean = new BeanWithSimpleProperty(true, null);

        Set<ConstraintViolation<BeanWithSimpleProperty>> violations = validator.validate(bean);
        assertThat(violations.isEmpty(), is(false));
        assertThat(messages(violations), containsInAnyOrder("validation.foo.required"));
        assertThat(paths(violations), containsInAnyOrder("foo"));
    }

    @ConditionalValidation(dependsOn = "condition", revalidate = {"foo", "bar"}, withGroups = FooChecks.class)
    public static class BeanWithSimpleProperty {

        private boolean condition;

        @NotNull(message = "validation.foo.required", groups = FooChecks.class)
        private String foo;

        @NotNull(message = "validation.bar.required", groups = BarChecks.class)
        private String bar;

        public BeanWithSimpleProperty(boolean condition, String foo) {
            this.condition = condition;
            this.foo = foo;
            this.bar = null;
        }

        public boolean isCondition() {
            return condition;
        }

        public String getFoo() {
            return foo;
        }

        public String getBar() {
            return bar;
        }
    }

    interface FooChecks {

    }

    interface BarChecks {

    }

}
