package seedu.address.model.reminders;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *  Represents a reminder.
 */

public class Reminder {

    private ObjectProperty<String> reminder;
    private ObjectProperty<DueDate> dueDate;

    /**
     * Every field must be present and not null.
     */
    public Reminder(String reminder, DueDate dueDate) {
        requireAllNonNull(reminder, dueDate);

        this.reminder = new SimpleObjectProperty<>(reminder);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
    }

    /**
     * Creates a copy of the given Reminder.
     */
    public Reminder(Reminder source) {
        this(source.getReminder(), source.getDueDate());
    }

    public void setReminder(String reminder) {
        this.reminder.set(requireNonNull(reminder));
    }

    public ObjectProperty<String> reminderProperty() {
        return reminder;
    }

    public String getReminder() {
        return reminder.get();
    }

    public void setDueDate(DueDate dueDate) {
        this.dueDate.set(requireNonNull(dueDate));
    }

    public ObjectProperty<DueDate> dueDateProperty() {
        return dueDate;
    }

    public DueDate getDueDate() {
        return dueDate.get();
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof Reminder
                && this.getReminder().equals(((Reminder) other).getReminder())
                && this.getDueDate().equals(((Reminder) other).getDueDate()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(reminder.get(), dueDate.get());
    }

    @Override
    public String toString() {
        return reminder + " " + dueDate;
    }
}
