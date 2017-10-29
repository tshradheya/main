package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.EditReminderCommand.EditReminderDescriptor;
import seedu.address.model.reminders.Reminder;

/**
 * A utility class to help with building EditReminderDescriptor objects.
 */
public class EditReminderDescriptorBuilder {

    private EditReminderDescriptor descriptor;

    public EditReminderDescriptorBuilder() {
        descriptor = new EditReminderDescriptor();
    }

    public EditReminderDescriptorBuilder(EditReminderDescriptor descriptor) {
        this.descriptor = new EditReminderDescriptor(descriptor);
    }

    public EditReminderDescriptorBuilder(Reminder reminder) {
        descriptor = new EditReminderDescriptor();
        descriptor.setReminder(reminder.getReminder());
        descriptor.setDate(reminder.getDate());
        descriptor.setTime(reminder.getTime());
    }

    /**
     * Sets the reminder of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withPhone(String reminder) {
        if (reminder.trim().isEmpty()) {
            throw new IllegalArgumentException("reminder is expected to be unique.");
        }
        descriptor.setReminder(reminder);
        return this;
    }
}
