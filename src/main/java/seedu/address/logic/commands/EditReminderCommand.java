package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.reminders.DueDate;
import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.exceptions.DuplicateReminderException;
import seedu.address.model.reminders.exceptions.ReminderNotFoundException;

/**
 * Edits the details of an existing reminder in the application.
 */
public class EditReminderCommand extends Command {

    public static final String COMMAND_WORD = "editReminder";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the reminder identified "
            + "by the index number. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_REMINDER + "REMINDER (cannot be empty)]"
            + "[" + PREFIX_DATE + "DATE]"
            + "[" + PREFIX_TIME + "TIME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMINDER + "Changed reminder";


    public static final String MESSAGE_EDIT_REMINDER_SUCCESS = "Edited Reminder: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists in iContacts.";

    private final Index index;
    private final EditReminderDescriptor editReminderDescriptor;

    /**
     * @param index of the reminder in the sorted reminder list to edit
     * @param editReminderDescriptor details to edit the reminder with
     */
    public EditReminderCommand(Index index, EditReminderDescriptor editReminderDescriptor) {
        requireNonNull(index);
        requireNonNull(editReminderDescriptor);

        this.index = index;
        this.editReminderDescriptor = editReminderDescriptor;
    }


    @Override
    public CommandResult execute() throws CommandException {
        List<Reminder> reminderList = model.getSortedReminderList();

        if (index.getZeroBased() >= reminderList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        Reminder reminderToEdit = reminderList.get(index.getZeroBased());
        Reminder editedReminder = createEditedReminder(reminderToEdit, editReminderDescriptor);

        try {
            model.updateReminder(reminderToEdit, editedReminder);
        } catch (DuplicateReminderException dre) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        } catch (ReminderNotFoundException pnfe) {
            throw new AssertionError("The target reminder cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder));
    }


    private static Reminder createEditedReminder (Reminder reminderToEdit,
                                                  EditReminderDescriptor editReminderDescriptor) {
        assert reminderToEdit != null;

        String updatedReminder = editReminderDescriptor.getReminder().orElse(reminderToEdit.getReminder());
        DueDate updatedDueDate = editReminderDescriptor.getDueDate(reminderToEdit.getDueDate())
                .orElse(reminderToEdit.getDueDate());

        return new Reminder(updatedReminder, updatedDueDate);
    }



    public static class EditReminderDescriptor {
        private String reminder;
        private String time;
        private String date;

        public EditReminderDescriptor() {}

        public EditReminderDescriptor(EditReminderDescriptor toCopy) {
            this.reminder = toCopy.reminder;
            this.time = toCopy.time;
            this.date = toCopy.date;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.reminder, this.time, this.date);
        }

        public void setReminder(String reminder) {
            this.reminder = reminder;
        }

        public Optional<String> getReminder() {
            return Optional.ofNullable(reminder);
        }

        public Optional<String> getDate() {
            return Optional.ofNullable(date);
        }

        public Optional<String> getTime() {
            return Optional.ofNullable(time);
        }

        public Optional<DueDate> getDueDate(DueDate original) {
            if (time == null && date == null) {
               return Optional.empty();
            } else if (time == null) {
                DueDate newDueDate;
                LocalTime originalTime = original.getLocalDateTime().toLocalTime();
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
                try {
                    newDueDate = new DueDate(date, originalTime.format(timeFormatter));
                } catch (IllegalValueException ive) {
                    throw new AssertionError("Date was already validated in EditReminderCommandParser.");
                }
                return Optional.of(newDueDate);
            } else if (date == null) {
                DueDate newDueDate;
                LocalDate originalDate = original.getLocalDateTime().toLocalDate();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                try {
                    newDueDate = new DueDate(originalDate.format(dateFormatter), time);
                } catch (IllegalValueException ive) {
                    throw new AssertionError("Time was already validated in EditReminderCommandParser.");
                }
                return Optional.of(newDueDate);
            } else {
                try {
                    DueDate newDueDate = new DueDate(date, time);
                    return Optional.of(newDueDate);
                } catch (IllegalValueException ive) {
                    throw new AssertionError("Date and Time were already validated.");
                }
            }
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditReminderDescriptor)) {
                return false;
            }

            // state check
            EditReminderDescriptor e = (EditReminderDescriptor) other;

            return getReminder().equals(e.getReminder())
                    && getDate().equals(e.getDate())
                    && getTime().equals(e.getTime());
        }
    }
}
