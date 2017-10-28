package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.reminders.DueDate.DUEDATE_FORMAT_MESSAGE;
import static seedu.address.model.reminders.DueDate.isValidDueDate;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditReminderCommand;
import seedu.address.logic.commands.EditReminderCommand.EditReminderDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

public class EditReminderCommandParser implements Parser<EditReminderCommand> {

    private static final String VALID_DATE = "01/01/2017";
    private static final String VALID_TIME = "0800";

    /**
     * Parses the given {@code String} of arguments in the context of the EditReminderCommand
     * and returns an EditReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER, PREFIX_DATE, PREFIX_TIME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditReminderCommand.MESSAGE_USAGE));
        }

        EditReminderDescriptor editReminderDescriptor = new EditReminderDescriptor();
        Optional<String> optionalReminder = argMultimap.getValue(PREFIX_REMINDER);
        String reminder;
        if (optionalReminder.isPresent()) {
            reminder = optionalReminder.get();
            if (reminder.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditReminderCommand.MESSAGE_USAGE));
            }
            editReminderDescriptor.setReminder(reminder);
        }

        Optional<String> optionalDate = argMultimap.getValue(PREFIX_DATE);
        String date;
        if (optionalDate.isPresent()) {
            date = optionalDate.get();
            if (!isValidDueDate(date, VALID_TIME)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DUEDATE_FORMAT_MESSAGE));
            }
            editReminderDescriptor.setDate(date);
        }

        Optional<String> optionalTime = argMultimap.getValue(PREFIX_TIME);
        String time;
        if (optionalTime.isPresent()) {
            time = optionalTime.get();
            if (!isValidDueDate(VALID_DATE, time)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DUEDATE_FORMAT_MESSAGE));
            }
            editReminderDescriptor.setTime(time);
        }


        if (!editReminderDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditReminderCommand.MESSAGE_NOT_EDITED);
        }

        return new EditReminderCommand(index, editReminderDescriptor);
    }
}
