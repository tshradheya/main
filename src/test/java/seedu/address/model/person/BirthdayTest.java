package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {
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

        // year too big -> returns false
        assertFalse(Birthday.isValidBirthday("01.01.10000"));

        // input format not correct -> returns false
        assertFalse(Birthday.isValidBirthday("1 January 1999"));
        assertFalse(Birthday.isValidBirthday("10.10/1999"));
        assertFalse(Birthday.isValidBirthday("10/ 10 / 1992"));

        // input values are not integers -> returns false
        assertFalse(Birthday.isValidBirthday("3.0/10.0/1995"));

        // not even a date -> returns false
        assertFalse(Birthday.isValidBirthday("Not a date"));

        // space -> returns false
        assertFalse(Birthday.isValidBirthday(" "));

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
        assertTrue(Birthday.isValidBirthday("29-4-2016"));
    }
}
