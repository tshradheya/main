package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.reminders.exceptions.DuplicateReminderException;
import seedu.address.model.reminders.Reminder;

/**
 *  Adds a reminder to the program.
 */

public class AddReminderCommand extends Command {

    public static final String COMMAND_WORD = "addReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder.\n"
            + "Parameters: REMINDER DATE TIME\n"
            + "REMINDER must be non-empty. DATE must be in the format dd-mm-yyyy, dd/mm/yyyy or dd.mm.yyyy, "
            + "and must be a valid date. TIME is in 24-hour format.\n"
            + "Example: addReminder Dinner with Family 22-11-2017 1700\n";

    public static final String MESSAGE_SUCCESS = "New reminder added!";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists!";

    private Reminder toAdd;

    /**
     * Creates an AddReminderCommand to add the specified {@code Reminder}
     */
    public AddReminderCommand(Reminder toAdd) {
        requireNonNull(toAdd);

        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.addReminder(toAdd);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (DuplicateReminderException dre) {
           throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddReminderCommand // instanceof handles nulls
                && toAdd.equals(((AddReminderCommand) other).toAdd));
    }
}
