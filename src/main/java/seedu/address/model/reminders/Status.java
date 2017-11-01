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

    private final Date dateOfReminder;
    private final Time timeOfReminder;

    private String status;

    /**
     * Initialize the status for this reminder.
     */
    public Status (Date dateOfReminder, Time timeOfReminder) {
        this.dateOfReminder = dateOfReminder;
        this.timeOfReminder = timeOfReminder;
        initStatus();
    }

    /**
     * Carries out the actual initializing of the status.
     */
    private void initStatus() {
        final LocalDate reminderDate = this.dateOfReminder.toLocalDate();
        final LocalTime reminderTime = this.timeOfReminder.toLocalTime();
        if (hasEventPast(reminderDate, reminderTime)) {
            status = STATUS_OVERDUE;
        } else if (isEventToday(reminderDate, reminderTime)) {
            status = STATUS_TODAY_MESSAGE;
        } else {
            status = String.format(STATUS_FORMAT_MESSAGE, getDaysUntilEvent(reminderDate));
        }
    }

    /**
     * Returns true of the event has already past.
     */
    private boolean hasEventPast(LocalDate reminderDate, LocalTime reminderTime) {
        final long daysUntilEvent = getDaysUntilEvent(reminderDate);
        final long minutesUntilEvent = getMinutesUntilEvent(reminderTime);
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
    private boolean isEventToday(LocalDate reminderDate, LocalTime reminderTime) {
        final long daysUntilEvent = getDaysUntilEvent(reminderDate);
        final long minutesUntilEvent = getMinutesUntilEvent(reminderTime);

        return daysUntilEvent == 0 && minutesUntilEvent >= 0;
    }

    /**
     * Return the number of days left until {@code reminderDate} (with respect to the date this object is created).
     */
    private long getDaysUntilEvent(LocalDate reminderDate) {
        return currentDate.until(reminderDate, ChronoUnit.DAYS);
    }

    /**
     * Return the difference in time between the current time and {@code reminderTime} in minutes
     * (with respect to the time this object is created).
     */
    private long getMinutesUntilEvent(LocalTime reminderTime) {
        return currentTime.until(reminderTime, ChronoUnit.MINUTES);
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
