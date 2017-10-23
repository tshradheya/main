package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminders.DueDate;
import seedu.address.model.reminders.Reminder;

/**
 * A utility class to help with building Reminder objects.
 */
public class ReminderBuilder {

    public static final String DEFAULT_REMINDER = "Drink coffee";
    public static final String DEFAULT_DATE = "01/11/2017";
    public static final String DEFAULT_TIME = "0800";

    private Reminder reminder;

    public ReminderBuilder() {
        try {
           DueDate dueDate = new DueDate(DEFAULT_DATE, DEFAULT_TIME);
           this.reminder = new Reminder(DEFAULT_REMINDER, dueDate);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default values cannot be wrong!");
        }
    }

    /**
     * Initializes the ReminderBuilder with the data of {@code reminderToCopy}.
     */
    public ReminderBuilder(Reminder reminderToCopy) {
        this.reminder = new Reminder(reminderToCopy);
    }

    /**
     * Sets the {@code reminder} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withReminder(String reminder) {
        this.reminder.setReminder(reminder);
        return this;
    }

    /**
     * Sets the {@code DueDate} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDueDate(String date, String time) {
        try {
            DueDate newDueDate = new DueDate(date, time);
            this.reminder.setDueDate(newDueDate);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Due date is expected to be unique");
        }
        return this;
    }

    public Reminder build() {
        return this.reminder;
    }
}
