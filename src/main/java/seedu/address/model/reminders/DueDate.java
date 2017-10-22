package seedu.address.model.reminders;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Wrapper around a LocalDateTime object to override its display format.
 */

public class DueDate {

    private static final String DATE_VALIDATION_REGEX = "(0[1-9]|[1-9]|1[0-9]|2[0-9]|3[01])[///./-]"
            + "(0[1-9]|1[0-2]|[1-9])[///./-](19|20)[0-9][0-9]";
    private static final String DATE_SPLIT_REGEX = "[///./-]";
    private static final String TIME_VALIDATION_REGEX = "(0[0-9]|1[0-9]|2[0-3])"
            + "(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])";
    private static final String DATE_FORMAT_MESSAGE = "Date must be in the format dd-mm-yyyy, dd/mm/yyyy or dd.mm.yyyy,"
            + "and must be a valid date.\n"
            + "Example: 22-10-2019, 23.12.1997, 24/12/1989";
    private static final String TIME_FORMAT_MESSAGE = "Time must be in 24-hour format.\n"
            + "Example: 0900, 2359, 1756";

    private static final int DATE_DAY_INDEX = 0;
    private static final int DATE_MONTH_INDEX = 1;
    private static final int DATE_YEAR_INDEX = 2;

    private final LocalDateTime dueDate;

    /**
     * Field is required to be non null.
     */
    public DueDate(String timeInLocalDateTimeFormat) {
        requireNonNull(timeInLocalDateTimeFormat);
        this.dueDate = LocalDateTime.parse(timeInLocalDateTimeFormat);
    }

    /**
     * Validates the given date and time.
     * @throws IllegalValueException if given date or time is invalid.
     */
    public DueDate(String userInputDate, String userInputTime) throws IllegalValueException {
        requireAllNonNull(userInputDate, userInputTime);
        if (!userInputDate.matches(DATE_VALIDATION_REGEX)) {
            throw new IllegalValueException(DATE_FORMAT_MESSAGE);
        }
        if (!userInputTime.matches(TIME_VALIDATION_REGEX)) {
            throw new IllegalValueException(TIME_FORMAT_MESSAGE);
        }

        String[] splitDate = userInputDate.split(DATE_SPLIT_REGEX);
        final int day = Integer.parseInt(splitDate[DATE_DAY_INDEX]);
        final int month = Integer.parseInt(splitDate[DATE_MONTH_INDEX]);
        final int year = Integer.parseInt(splitDate[DATE_YEAR_INDEX]);

        final int hour = Integer.parseInt(userInputTime.substring(0, 2));
        final int minute = Integer.parseInt(userInputTime.substring(2));

        this.dueDate = LocalDateTime.of(year, month, day, hour, minute);
    }

    public LocalDateTime getLocalDateTime() {
        return dueDate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        LocalDate tempDate = dueDate.toLocalDate();
        LocalTime tempTime = dueDate.toLocalTime();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        builder.append(tempDate.format(dateFormatter));
        builder.append("\n");
        builder.append(tempTime.format(timeFormatter));
        return builder.toString();
    }


}
