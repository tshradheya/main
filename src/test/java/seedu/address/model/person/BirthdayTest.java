package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BirthdayTest {

    private final int TEST_MONTH = 5;
    private final int TEST_DAY = 10;
    private final String TEST_BIRTHDAY = TEST_DAY + "-" + TEST_MONTH + "-1990";
    private final int WRONG_MONTH = 6;
    private final int WRONG_DAY = 9;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getMonthOfBirthday() throws Exception {
        Birthday birthday = new Birthday(TEST_BIRTHDAY);

        // correct month -> equal
        assertEquals(TEST_MONTH, birthday.getMonthOfBirthday());

        // wrong month -> not equal
        assertNotEquals(WRONG_MONTH, birthday.getMonthOfBirthday());

        // no birthday field -> equal to Birthday.EMPTY_BIRTHDAY_FIELD_MONTH
        assertEquals(Birthday.EMPTY_BIRTHDAY_FIELD_MONTH, new Birthday("").getMonthOfBirthday());
    }

    @Test
    public void getDayOfBirthday() throws Exception {
        Birthday birthday = new Birthday(TEST_BIRTHDAY);

        // correct day -> equal
        assertEquals(TEST_DAY, birthday.getDayOfBirthday());

        // wrong month -> not equal
        assertNotEquals(WRONG_DAY, birthday.getDayOfBirthday());

        // no birthday field -> equal to Birthday.EMPTY_BIRTHDAY_FIELD_MONTH
        assertEquals(Birthday.EMPTY_BIRTHDAY_FIELD_DAY, new Birthday("").getDayOfBirthday());
    }

    @Test
    public void isValidBirthday() {
        // day out of bounds -> returns false
        assertFalse(Birthday.isValidBirthday("32.3.1995"));
        assertFalse(Birthday.isValidBirthday("31.4.1995"));
        assertFalse(Birthday.isValidBirthday("0.1.1995"));

        // month out of bounds -> returns false
        assertFalse(Birthday.isValidBirthday("21.13.1995"));
        assertFalse(Birthday.isValidBirthday("21.0.1995"));

        // year too small -> returns false
        assertFalse(Birthday.isValidBirthday("01.01.999"));
        assertFalse(Birthday.isValidBirthday("01.01.1899"));

        // year too big -> returns false
        assertFalse(Birthday.isValidBirthday("01.01.10000"));
        assertFalse(Birthday.isValidBirthday("01.01.2100"));

        // input format not correct -> returns false
        assertFalse(Birthday.isValidBirthday("1 January 1999"));
        assertFalse(Birthday.isValidBirthday("10/ 10 / 1992"));

        // input values are not integers -> returns false
        assertFalse(Birthday.isValidBirthday("3.0/10.0/1995"));

        // not even a date -> returns false
        assertFalse(Birthday.isValidBirthday("Not a date"));

        // wrong leap day date -> returns false
        assertFalse(Birthday.isValidBirthday("29/2/2017"));

        // space -> returns true
        assertTrue(Birthday.isValidBirthday(" "));

        // empty string -> returns true
        assertTrue(Birthday.isValidBirthday(""));

        // valid dates using / separator -> returns true
        assertTrue(Birthday.isValidBirthday("31/3/1995"));
        assertTrue(Birthday.isValidBirthday("31/03/1995"));
        assertTrue(Birthday.isValidBirthday("01/03/1995"));

        // valid dates using - separator -> returns true
        assertTrue(Birthday.isValidBirthday("01-03-1995"));
        assertTrue(Birthday.isValidBirthday("31-03-1995"));
        assertTrue(Birthday.isValidBirthday("01-03-1995"));

        // valid dates using . separator -> returns true
        assertTrue(Birthday.isValidBirthday("01.03.1995"));
        assertTrue(Birthday.isValidBirthday("31.03.1995"));
        assertTrue(Birthday.isValidBirthday("01.03.1995"));

        // valid leap year day -> returns true
        assertTrue(Birthday.isValidBirthday("29-2-2016"));
        assertTrue(Birthday.isValidBirthday("29-2-2000"));

        // flexible day-month-year separator -> returns true
        assertTrue(Birthday.isValidBirthday("01/03.1995"));
        assertTrue(Birthday.isValidBirthday("31.03/1995"));
        assertTrue(Birthday.isValidBirthday("01/03-1995"));
    }

}
