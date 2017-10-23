package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminders.DueDate;
import seedu.address.model.reminders.Reminder;

/**
 * Parses input arguments and creates a new AddReminderCommand object
 */
public class AddReminderCommandParser implements Parser<AddReminderCommand> {

    private static final String ARGUMENT_SPLIT_REGEX = "[\\s]+";
    private static final int REMINDER_SMALLEST_SEGMENT_NUMBER = 3;

    /**
     * Parses the given {@code String} of arguments in the context of the AddReminderCommand
     * and returns an AddReminderCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        final String reminder;
        final String userInputDate;
        final String userInputTime;

        String[] splitArgs = args.trim().split(ARGUMENT_SPLIT_REGEX);
        if (splitArgs.length < REMINDER_SMALLEST_SEGMENT_NUMBER) {
            throw new ParseException("Format invalid.\n" + AddReminderCommand.MESSAGE_USAGE);
        }
        userInputTime = splitArgs[splitArgs.length - 1];
        userInputDate = splitArgs[splitArgs.length - 2];
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < splitArgs.length - 2; i++) {
            builder.append(splitArgs[i]);
            builder.append(" ");
        }
        reminder = builder.toString().trim();

        try {
            DueDate dueDate = new DueDate(userInputDate, userInputTime);
            Reminder toAdd = new Reminder(reminder, dueDate);
            return new AddReminderCommand(toAdd);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage());
        }
    }

}
