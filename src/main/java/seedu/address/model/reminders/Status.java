package seedu.address.model.reminders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Represents a Reminder's status in the program.
 * Guarantees: immutable.
 */
public class Status {

    private static final String STATUS_FORMAT_SINGLE_MESSAGE = "Status: %1$s day left.";
    private static final String STATUS_FORMAT_MESSAGE = "Status: %1$s days left.";
    private static final String STATUS_TODAY_MESSAGE = "Event happening today!";
    private static final String STATUS_OVERDUE = "Event has past.";

    private final int ONE_DAY = 1;

    private final LocalDate currentDate;
    private final LocalTime currentTime;

    private final LocalDate dateOfReminder;
    private final LocalTime timeOfReminder;

    private String status;

    /**
     * Initialize the status for this reminder.
     */
    public Status(Reminder reminder) {
        currentDate = LocalDate.now();
        currentTime = LocalTime.now();
        this.dateOfReminder = reminder.getDate().toLocalDate();
        this.timeOfReminder = reminder.getTime().toLocalTime();
        initStatus();
    }

    /**
     * This constructor is used for testing purposes only.
     */
    private Status(Reminder reminder, LocalDate defaultDate, LocalTime defaultTime) {
        this.currentDate = defaultDate;
        this.currentTime = defaultTime;
        this.dateOfReminder = reminder.getDate().toLocalDate();
        this.timeOfReminder = reminder.getTime().toLocalTime();
        initStatus();
    }

    /**
     * Return a Status instance that is used for testing, initialized with {@code reminder}, {@code defaultDate}
     * and {@code defaultTime}.
     */
    public static Status getStatusTestInstance(Reminder reminder, LocalDate defaultDate, LocalTime defaultTime) {
        Status testInstance = new Status(reminder, defaultDate, defaultTime);
        return testInstance;
    }

    /**
     * Returns true if the event has already past.
     */
    public boolean hasEventPast() {
        final long daysUntilEvent = getDaysUntilEvent();
        final long minutesUntilEvent = getMinutesUntilEvent();
        if (daysUntilEvent > 0) {
            return false;
        } else if (daysUntilEvent == 0) {
            if (minutesUntilEvent >= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if the event is happening today (with respect to the date and time this object is created).
     */
    public boolean isEventToday() {
        final long daysUntilEvent = getDaysUntilEvent();
        final long minutesUntilEvent = getMinutesUntilEvent();

        return daysUntilEvent == 0 && minutesUntilEvent >= 0;
    }

    /**
     * Returns true if the event is happening within three days
     * (with respect to the date and time this object is created.)
     */
    public boolean isEventWithinThreeDays() {
        final long daysUntilEvent = getDaysUntilEvent();
        final long minutesUntilEvent = getMinutesUntilEvent();
        if (daysUntilEvent < 0 || daysUntilEvent > 3) {
            return false;
        }
        if (daysUntilEvent == 0 && minutesUntilEvent < 0) {
            return false;
        }
        return true;
    }

    /**
     * Carries out the actual initializing of the status.
     */
    private void initStatus() {
        if (hasEventPast()) {
            status = STATUS_OVERDUE;
        } else if (isEventToday()) {
            status = STATUS_TODAY_MESSAGE;
        } else {
            final long daysUntilEvent = getDaysUntilEvent();
            if (daysUntilEvent == ONE_DAY) {
                status = String.format(STATUS_FORMAT_SINGLE_MESSAGE, ONE_DAY);
            } else {
                status = String.format(STATUS_FORMAT_MESSAGE, getDaysUntilEvent());
            }
        }
    }

    /**
     * Return the number of days left until {@code reminderDate} (with respect to the date this object is created).
     */
    private long getDaysUntilEvent() {
        return currentDate.until(dateOfReminder, ChronoUnit.DAYS);
    }

    /**
     * Return the difference in time between the current time and {@code reminderTime} in minutes
     * (with respect to the time this object is created).
     */
    private long getMinutesUntilEvent() {
        return currentTime.until(timeOfReminder, ChronoUnit.MINUTES);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, dateOfReminder, timeOfReminder);
    }

    @Override
    public String toString() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof Status
                && this.status.equals(((Status) other).status));
    }
}
