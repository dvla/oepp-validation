package uk.gov.dvla.validation.constraints;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class VRMValidatorTest {

    private String regMark;
    private boolean expectedValidity;

    public VRMValidatorTest(String regMark, boolean expectedValidity) {
        this.regMark = regMark;
        this.expectedValidity = expectedValidity;
    }

    @Parameterized.Parameters
    public static Collection testData() {
        final String EMPTY = "";
        final String SPACES = "   ";
        final String TOO_SHORT = "A";
        final String TOO_LONG = "AAAA9999";
        final String TOO_MANY_LETTERS = "AAAAAAA";
        final String TOO_MANY_NUMBERS = "99999AA";
        final String ONLY_NUMBERS = "999";
        final String ONLY_LETTERS = "AAA";
        final String FOUR_LETTERS_ON_END_1 = "9AAAA";
        final String FOUR_LETTERS_ON_END_2 = "99AAAA";
        final String FOUR_LETTERS_ON_END_3 = "999AAAA";

        final String LETTER_NUMBER_LETTER = "A9A";
        final String TWO_LETTERS_NUMBER_LETTER = "AA9A";
        final String LETTER_NUMBER_TWO_LETTERS = "A9AA";
        final String LETTER_TWO_NUMBERS_TWO_LETTERS = "A99AA";

        final String VALID_1 = "A9"; //([A-Za-z]{1,3}[0-9]{1,3})
        final String VALID_2 = "A99"; //([A-Za-z]{1,3}[0-9]{1,3})
        final String VALID_3 = "A999"; //([A-Za-z]{1,3}[0-9]{1,3})

        final String VALID_4 = "A9999"; //([A-Za-z]{1,2}[0-9]{1,4})
        final String VALID_5 = "AA9999"; //([A-Za-z]{1,2}[0-9]{1,4})

        final String VALID_6 = "AA9"; //([A-Za-z]{1,3}[0-9]{1,3})
        final String VALID_7 = "AA99"; //([A-Za-z]{1,3}[0-9]{1,3})
        final String VALID_8 = "AA999"; //([A-Za-z]{1,3}[0-9]{1,3})

        final String VALID_9 = "AAA9"; //([A-Za-z]{3}[0-9]{1,4})
        final String VALID_10 = "AAA99"; //([A-Za-z]{3}[0-9]{1,4})
        final String VALID_11 = "AAA999"; //([A-Za-z]{3}[0-9]{1,4})
        final String VALID_12 = "AAA9999"; //([A-Za-z]{3}[0-9]{1,4})

        final String VALID_13 = "Y9AAA"; //([A-Za-z][0-9]{1,3}[A-Za-z]{3})
        final String VALID_14 = "Y99AAA"; //([A-Za-z][0-9]{1,3}[A-Za-z]{3})
        final String VALID_15 = "Y999AAA"; //([A-Za-z][0-9]{1,3}[A-Za-z]{3})

        final String VALID_16 = "AAA9Y"; //([A-Za-z]{3}[0-9]{1,3}[A-Za-z])
        final String VALID_17 = "AAA99Y"; //([A-Za-z]{3}[0-9]{1,3}[A-Za-z])
        final String VALID_18 = "AAA999Y"; //([A-Za-z]{3}[0-9]{1,3}[A-Za-z])

        final String VALID_19 = "AA99AAA"; //([A-Za-z]{2}[0-9]{2}[A-Za-z]{3})

        final String VALID_20 = "9A"; //([0-9]{1,4}[A-Za-z]{1,3})
        final String VALID_21 = "9AA"; //([0-9]{1,4}[A-Za-z]{1,3})
        final String VALID_22 = "9AAA"; //([0-9]{1,4}[A-Za-z]{1,3})
        final String VALID_23 = "99A"; //([0-9]{1,4}[A-Za-z]{1,3})
        final String VALID_24 = "99AA"; //([0-9]{1,4}[A-Za-z]{1,3})
        final String VALID_25 = "99AAA"; //([0-9]{1,4}[A-Za-z]{1,3})
        final String VALID_26 = "999A"; //([0-9]{1,4}[A-Za-z]{1,3})
        final String VALID_27 = "999AA"; //([0-9]{1,4}[A-Za-z]{1,3})
        final String VALID_28 = "999AAA"; //([0-9]{1,4}[A-Za-z]{1,3})
        final String VALID_29 = "9999A"; //([0-9]{1,4}[A-Za-z]{1,3})
        final String VALID_30 = "9999AA"; //([0-9]{1,4}[A-Za-z]{1,3})
        final String VALID_31 = "9999AAA"; //([0-9]{1,4}[A-Za-z]{1,3})

        final String VALID_WITH_WHITESPACE_1 = " A 9 A    A    A    ";
        final String VALID_WITH_WHITESPACE_2 = " A9    ";

        return Arrays.asList(new Object[][]{
                {EMPTY, false},
                {SPACES, false},
                {TOO_SHORT, false},
                {TOO_MANY_LETTERS, false},
                {TOO_MANY_NUMBERS, false},
                {TOO_LONG, false},
                {ONLY_LETTERS, false},
                {ONLY_NUMBERS, false},
                {FOUR_LETTERS_ON_END_1, false},
                {FOUR_LETTERS_ON_END_2, false},
                {FOUR_LETTERS_ON_END_3, false},
                {LETTER_NUMBER_LETTER, false},
                {TWO_LETTERS_NUMBER_LETTER, false},
                {LETTER_NUMBER_TWO_LETTERS, false},
                {LETTER_TWO_NUMBERS_TWO_LETTERS, false},
                {null, true},
                {VALID_1, true},
                {VALID_2, true},
                {VALID_3, true},
                {VALID_4, true},
                {VALID_5, true},
                {VALID_6, true},
                {VALID_7, true},
                {VALID_8, true},
                {VALID_9, true},
                {VALID_10, true},
                {VALID_11, true},
                {VALID_12, true},
                {VALID_13, true},
                {VALID_14, true},
                {VALID_15, true},
                {VALID_16, true},
                {VALID_17, true},
                {VALID_18, true},
                {VALID_19, true},
                {VALID_20, true},
                {VALID_21, true},
                {VALID_22, true},
                {VALID_23, true},
                {VALID_24, true},
                {VALID_25, true},
                {VALID_26, true},
                {VALID_27, true},
                {VALID_28, true},
                {VALID_29, true},
                {VALID_30, true},
                {VALID_31, true},
                {VALID_WITH_WHITESPACE_1, true},
                {VALID_WITH_WHITESPACE_2, true}
        });
    }

    @Test
    public void validateCaseReferenceNumber() {
        VRMValidator validator = new VRMValidator();
        boolean result = validator.isValid(regMark, null);
        assertThat(result, is(expectedValidity));
    }

}
