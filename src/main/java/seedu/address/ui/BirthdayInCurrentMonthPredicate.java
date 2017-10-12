package seedu.address.ui;

import java.util.Calendar;
import java.util.function.Predicate;

import seedu.address.model.person.Birthday;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyPerson}'s birthday month is the current month.
 * If a {@code ReadOnlyPerson} does not have a birthday recorded, return false.
 */
public class BirthdayInCurrentMonthPredicate implements Predicate<ReadOnlyPerson> {
    private final int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

    @Override
    public boolean test(ReadOnlyPerson person) {
        final int month = person.getBirthday().getMonthOfBirthday();
        if (month == Birthday.EMPTY_BIRTHDAY_FIELD_MONTH) {
            return false;
        }
        return currentMonth == month;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BirthdayInCurrentMonthPredicate // instanceof handles nulls
                && this.currentMonth == ((BirthdayInCurrentMonthPredicate) other).currentMonth); // state check
    }
}
