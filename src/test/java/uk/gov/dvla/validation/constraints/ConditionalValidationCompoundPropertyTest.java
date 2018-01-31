package uk.gov.dvla.validation.constraints;

import org.junit.Test;
import uk.gov.dvla.validation.constraints.ConditionalValidationCompoundPropertyTest.BeanWithCompoundProperty.Compound;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConditionalValidationCompoundPropertyTest extends BaseConstraintTest {

    @Test
    public void validWhenConditionEvaluatesToFalse() {
        BeanWithCompoundProperty bean = new BeanWithCompoundProperty(false, null);

        Set<ConstraintViolation<BeanWithCompoundProperty>> violations = validator.validate(bean);
        assertThat(violations.isEmpty(), is(true));
    }

    @Test
    public void validWhenConditionEvaluatesToTrueAndAllIsValid() {
        BeanWithCompoundProperty bean = new BeanWithCompoundProperty(false, new Compound("value"));

        Set<ConstraintViolation<BeanWithCompoundProperty>> violations = validator.validate(bean);
        assertThat(violations.isEmpty(), is(true));
    }

    @Test
    public void invalidWhenConditionEvaluatesToTrueAndBeanPropertyIsInvalid() {
        BeanWithCompoundProperty bean = new BeanWithCompoundProperty(true, null);

        Set<ConstraintViolation<BeanWithCompoundProperty>> violations = validator.validate(bean);
        assertThat(violations.isEmpty(), is(false));
        assertThat(messages(violations), containsInAnyOrder("validation.compound.required"));
        assertThat(paths(violations), containsInAnyOrder("compound"));
    }

    @Test
    public void invalidWhenConditionEvaluatesToTrueAndBeanPropertyIsValidButCompoundPropertyIsInvalid() {
        BeanWithCompoundProperty bean = new BeanWithCompoundProperty(true, new Compound(null));

        Set<ConstraintViolation<BeanWithCompoundProperty>> violations = validator.validate(bean);
        assertThat(violations.isEmpty(), is(false));
        assertThat(messages(violations), containsInAnyOrder("validation.value.required"));
        assertThat(paths(violations), containsInAnyOrder("compound.value"));
    }

    @ConditionalValidation(dependsOn = "condition", revalidate = {"compound"}, withGroups = CompoundChecks.class)
    public static class BeanWithCompoundProperty {

        private boolean condition;

        @NotNull(message = "validation.compound.required", groups = CompoundChecks.class)
        @Valid
        private Compound compound;

        public BeanWithCompoundProperty(boolean condition, Compound compound) {
            this.condition = condition;
            this.compound = compound;
        }

        public boolean isCondition() {
            return condition;
        }

        public Compound getCompound() {
            return compound;
        }

        public static class Compound {
            @NotNull(message = "validation.value.required", groups = CompoundChecks.class)
            private String value;

            public Compound(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }
        }

    }

    interface CompoundChecks {

    }

}
