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

    private static final LocalDate currentDate = LocalDate.now();
    private static final LocalTime currentTime = LocalTime.now();

    private final String STATUS_FORMAT_MESSAGE = "Status: %1$s days left.";
    private final String STATUS_TODAY_MESSAGE = "Event happening today!";
    private final String STATUS_OVERDUE = "Event has past.";

    private final LocalDate dateOfReminder;
    private final LocalTime timeOfReminder;

    private String status;

    /**
     * Initialize the status for this reminder.
     */
    public Status (Date dateOfReminder, Time timeOfReminder) {
        this.dateOfReminder = dateOfReminder.toLocalDate();
        this.timeOfReminder = timeOfReminder.toLocalTime();
        initStatus();
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
            status = String.format(STATUS_FORMAT_MESSAGE, getDaysUntilEvent());
        }
    }

    /**
     * Returns true of the event has already past.
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
}
