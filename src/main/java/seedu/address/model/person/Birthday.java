package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS = "Birthday must be in the following format: "
            + "dd/mm/yyyy, dd.mm.yyyy or dd-mm-yyyy.\n"
            + "Leading zeroes are allowed for the day and month field. The year field must have 4 digits.\n"
            + "Example: 21/10/1995, 21-05-1996. 8.10.1987";
    public static final int EMPTY_BIRTHDAY_FIELD_MONTH = 0;
    private static final int[] MONTH_TO_DAY_MAPPING = {31, 28, 31, 30, 31, 30, 31, 31,
        30, 31, 30, 31};
    private static final String BIRTHDAY_DASH_SEPARATOR = "-";
    private static final String BIRTHDAY_FORWARD_SLASH_SEPARATOR = "/";
    private static final String BIRTHDAY_DOT_SEPARATOR = "\\.";

    private static final String EMPTY_STRING = "";

    private static final int ZERO_BASED_ADJUSTMENT = 1;

    private static final int SMALLEST_POSSIBLE_DAY = 1;

    private static final int SMALLEST_POSSIBLE_MONTH = 1;
    private static final int LARGEST_POSSIBLE_MONTH = 12;

    private static final int SMALLEST_ALLOWED_YEAR = 1000;
    private static final int LARGEST_ALLOWED_YEAR = 9999;

    private static final int LEAP_YEAR_MONTH_FEBRUARY = 2;
    private static final int LEAP_YEAR_DAY = 29;
    private static final int LEAP_YEAR_REQUIREMENT_FIRST = 4;
    private static final int LEAP_YEAR_REQUIREMENT_SECOND = 100;
    private static final int LEAP_YEAR_REQUIREMENT_THIRD = 400;

    private static final int DATE_DAY_INDEX = 0;
    private static final int DATE_MONTH_INDEX = 1;
    private static final int DATE_YEAR_INDEX = 2;

    private static final int NUMBER_OF_LOGICAL_SEGMENTS_IN_DATE = 3;

    public final String value;


    /**
     * Validates the given birthday.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);

        if (!isValidBirthday(birthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }

        if (birthday.isEmpty()) {
            this.value = EMPTY_STRING;
        } else {
            int[] processedSplitDate = processDate(birthday);
            this.value = convertToDefaultDateFormat(processedSplitDate);
        }
    }

    /**
     * Get the month of the birthday in this Birthday object.
     * If the birthday field is empty, return EMPTY_BIRTHDAY_FIELD_MONTH
     */
    public int getMonthOfBirthday() {
        if (value.isEmpty()) {
            return EMPTY_BIRTHDAY_FIELD_MONTH;
        }
        String[] splitDate = value.split(BIRTHDAY_DASH_SEPARATOR);
        try {
            final int month = Integer.parseInt(splitDate[DATE_MONTH_INDEX]);
            return month;
        } catch (NumberFormatException nfe) {
            throw new AssertionError("Should not happen");
        }
    }

    /**
     * Converts date in integer array into dd-mm-yyyy String format.
     */
    private static String convertToDefaultDateFormat(int[] processedSplitDate) {
        StringBuilder builder = new StringBuilder();
        builder.append(processedSplitDate[DATE_DAY_INDEX]);
        builder.append(BIRTHDAY_DASH_SEPARATOR);
        builder.append(processedSplitDate[DATE_MONTH_INDEX]);
        builder.append(BIRTHDAY_DASH_SEPARATOR);
        builder.append(processedSplitDate[DATE_YEAR_INDEX]);
        return builder.toString();
    }

    /**
     * Split the date String into integer array with a suitable separator for easy processing.
     *
     * @throws NumberFormatException if one segment of the date String cannot be parsed into an integer.
     */
    private static int[] processDate(String date) throws NumberFormatException {
        requireNonNull(date);

        String[] splitDate = getSplitDate(date);
        int[] numericalSplitDate = new int[splitDate.length];

        for (int i = 0; i < splitDate.length; i++) {
            splitDate[i] = removeLeadingZeroes(splitDate[i]);
            numericalSplitDate[i] = Integer.parseInt(splitDate[i]);
        }

        return numericalSplitDate;
    }

    /**
     * Split the date String into a String array with a suitable separator.
     */
    private static String[] getSplitDate(String date) {
        requireNonNull(date);

        String[] splitDate;
        if (date.contains(BIRTHDAY_DASH_SEPARATOR)) {
            splitDate = date.split(BIRTHDAY_DASH_SEPARATOR);
        } else if (date.contains(BIRTHDAY_FORWARD_SLASH_SEPARATOR)) {
            splitDate = date.split(BIRTHDAY_FORWARD_SLASH_SEPARATOR);
        } else {
            splitDate = date.split(BIRTHDAY_DOT_SEPARATOR);
        }
        return splitDate;
    }

    /**
     * Remove leading zeroes from each segment of the date to allow parsing using Integer.parseInt.
     */
    private static String removeLeadingZeroes(String toRemove) {
        requireNonNull(toRemove);

        while (!toRemove.isEmpty() && toRemove.startsWith("0")) {
            toRemove = toRemove.substring(1);
        }
        return toRemove;
    }

    /**
     * Returns true if the birthday input is valid.
     * A birthday input is valid if it is a valid date, or an empty String.
     */
    public static boolean isValidBirthday(String birthday) {
        requireNonNull(birthday);

        if (birthday.isEmpty()) {
            return true;
        }

        final int[] processedSplitDate;

        try {
            processedSplitDate = processDate(birthday);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return isValidDate(processedSplitDate);
    }

    /**
     * Returns true if the date is valid. A date is valid if it is possible for it to exist.
     * E.g. 31/4/1995 is not a valid date because April only has 30 days.
     */
    private static boolean isValidDate(int[] processedSplitDate) {
        requireNonNull(processedSplitDate);

        if (processedSplitDate.length != NUMBER_OF_LOGICAL_SEGMENTS_IN_DATE) {
            return false;
        }
        if (isLeapYearDate(processedSplitDate)) {
            return true;
        }
        return isYearValid(processedSplitDate[DATE_YEAR_INDEX])
                && isDayAndMonthValid(processedSplitDate[DATE_DAY_INDEX], processedSplitDate[DATE_MONTH_INDEX]);
    }

    /**
     * Returns true if the month and day is valid.
     * The month and day is valid if the day can exist in the specific month.
     */
    private static boolean isDayAndMonthValid(int day, int month) {
        if (!isMonthValid(month)) {
            return false;
        }

        final int dayUpperLimitForMonth = MONTH_TO_DAY_MAPPING[month - ZERO_BASED_ADJUSTMENT];

        return isDayValid(dayUpperLimitForMonth, day);
    }

    /**
     * Returns true if the day is valid.
     * A day is valid if it is at least 1 and smaller than or equal to the largest possible day for the specific month.
     */
    private static boolean isDayValid(int dayUpperLimitForMonth, int day) {
        return day >= SMALLEST_POSSIBLE_DAY && day <= dayUpperLimitForMonth;
    }

    /**
     * Returns true if the month is valid.
     */
    private static boolean isMonthValid(int month) {
        return month >= SMALLEST_POSSIBLE_MONTH && month <= LARGEST_POSSIBLE_MONTH;
    }

    /**
     * Returns true if the year is valid.
     * A year is valid if has at least 4 digits.
     */
    private static boolean isYearValid(int year) {
        return year >= SMALLEST_ALLOWED_YEAR && year <= LARGEST_ALLOWED_YEAR;
    }

    /**
     * Returns true if the date is a leap year day.
     * A leap year day must be on 29 April.
     */
    private static boolean isLeapYearDate(int[] processedSplitDate) {
        return processedSplitDate[DATE_DAY_INDEX] == LEAP_YEAR_DAY
                && processedSplitDate[DATE_MONTH_INDEX] == LEAP_YEAR_MONTH_FEBRUARY
                && isLeapYear(processedSplitDate[DATE_YEAR_INDEX]);


    }

    /**
     * Returns true if the year is a valid leap year.
     * Algorithm to determine is a year is a valid leap year:
     * https://support.microsoft.com/en-us/help/214019/method-to-determine-whether-a-year-is-a-leap-year
     */
    private static boolean isLeapYear(int year) {
        if (year % LEAP_YEAR_REQUIREMENT_FIRST == 0 && year % LEAP_YEAR_REQUIREMENT_SECOND != 0) {
            return true;
        } else if (year % LEAP_YEAR_REQUIREMENT_FIRST == 0 && year % LEAP_YEAR_REQUIREMENT_SECOND == 0
                && year % LEAP_YEAR_REQUIREMENT_THIRD == 0) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof Birthday
                && this.value.equals(((Birthday) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
