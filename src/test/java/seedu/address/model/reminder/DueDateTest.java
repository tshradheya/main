package seedu.address.model.reminder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.reminders.DueDate;

public class DueDateTest {
    @Test
    public void isValidDueDate() {
        // day out-of-bounds -> returns false
        assertFalse(DueDate.isValidDueDate("0/01/2017", "0800"));
        assertFalse(DueDate.isValidDueDate("31/04/2017", "0800"));

        // month out-of-bounds -> returns false
        assertFalse(DueDate.isValidDueDate("01/13/2017", "0800"));
        assertFalse(DueDate.isValidDueDate("01/00/2017", "0800"));

        // year out-of-bounds -> returns false
        assertFalse(DueDate.isValidDueDate("01/13/2100", "0800"));
        assertFalse(DueDate.isValidDueDate("01/13/1899", "0800"));

        // incorrect date input format -> returns false
        assertFalse(DueDate.isValidDueDate("1 Mar 2017", "0800"));

        // input is not even a date -> returns false
        assertFalse(DueDate.isValidDueDate("Not a date", "0800"));

        // invalid 24-hr time -> returns false
        assertFalse(DueDate.isValidDueDate("01/01/2017", "2400"));

        // incorrect time input format -> returns false
        assertFalse(DueDate.isValidDueDate("01/01/2017", "8AM"));

        // input is not even a time -> returns false
        assertFalse(DueDate.isValidDueDate("01/01/2017", "Not a time"));

        // valid date and time input -> returns true
        assertTrue(DueDate.isValidDueDate("01/01/2017", "0800"));
    }

    @Test
    public void equals() throws Exception {

        DueDate dueDate1 = new DueDate("01/01/2017", "0800");

        // different date -> returns false
        DueDate dueDate2 = new DueDate("02/01/2017", "0800");
        assertFalse(dueDate1.equals(dueDate2));

        // different time -> returns false
        DueDate dueDate3 = new DueDate("01/01/2017", "0900");
        assertFalse(dueDate1.equals(dueDate3));

        // different type -> returns false
        assertFalse(dueDate1.equals(100));

        // null -> returns false
        assertFalse(dueDate1.equals(null));

        // same object -> returns true
        assertTrue(dueDate1.equals(dueDate1));

        // same values -> returns true
        DueDate dueDate4 = new DueDate("01/01/2017", "0800");
        assertTrue(dueDate1.equals(dueDate4));
    }
}
