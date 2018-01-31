package uk.gov.dvla.validation.constraints;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.validation.ConstraintViolation;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class LOCSCaseNumberTest extends BaseConstraintTest {

    @Parameterized.Parameters(name = "{0} -> {1}")
    public static Object[][] testData() {
        Long TOO_LOW = 9999999L;
        Long TOO_HIGH = 100000000L;
        Long VALID_LOWER_BAND = 10000000L;
        Long VALID_UPPER_BAND = 99999999L;

        Optional<String> INVALID_VALIDATION_MESSAGE_KEY = Optional.of("validation.caseNumber.invalid");
        Optional<String> VALID_VALIDATION_MESSAGE_KEY = Optional.empty();

        return new Object[][]{
                {TOO_LOW, INVALID_VALIDATION_MESSAGE_KEY},
                {TOO_HIGH, INVALID_VALIDATION_MESSAGE_KEY},
                {null, VALID_VALIDATION_MESSAGE_KEY},
                {VALID_LOWER_BAND, VALID_VALIDATION_MESSAGE_KEY},
                {VALID_UPPER_BAND, VALID_VALIDATION_MESSAGE_KEY}
        };
    }

    private Long caseNumber;
    private String[] expectedValidationMessages;

    public LOCSCaseNumberTest(Long caseNumber, Optional<String> expectedValidationMessage) {
        this.caseNumber = caseNumber;
        if (expectedValidationMessage.isPresent()) {
            this.expectedValidationMessages = new String[]{expectedValidationMessage.get()};
        } else {
            this.expectedValidationMessages = new String[0];
        }
    }

    @Test
    public void test() {
        Set<ConstraintViolation<Bean>> violations = validator.validate(new Bean(caseNumber));
        assertThat(messages(violations), containsInAnyOrder(expectedValidationMessages));
    }

    @SuppressWarnings("unused")
    private static class Bean {

        @LOCS.CaseNumber(message = "validation.caseNumber.invalid")
        private Long caseNumber;

        private Bean(Long caseNumber) {
            this.caseNumber = caseNumber;
        }

        public Long getCaseNumber() {
            return caseNumber;
        }
    }

}
