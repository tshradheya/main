package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMINDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMINDER_DESC_DATE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMINDER_DESC_TIME;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DESC_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DESC_DATE;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DESC_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.model.reminders.DueDate;
import seedu.address.model.reminders.Reminder;
import seedu.address.testutil.ReminderBuilder;

public class AddReminderParserTest {

    private AddReminderCommandParser parser = new AddReminderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        Reminder expectedReminder = new ReminderBuilder().build();
        assertParseSuccess(parser, REMINDER_DESC_COFFEE + REMINDER_DESC_DATE
                + REMINDER_DESC_TIME, new AddReminderCommand(expectedReminder));
    }

    @Test
    public void parse_fieldMissing_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE);

        // missing reminder
        assertParseFailure(parser, REMINDER_DESC_DATE + REMINDER_DESC_TIME, expectedMessage);

        // missing date
        assertParseFailure(parser, REMINDER_DESC_COFFEE + REMINDER_DESC_TIME, expectedMessage);

        // missing time
        assertParseFailure(parser, REMINDER_DESC_COFFEE + REMINDER_DESC_DATE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE);

        //invalid reminder
        assertParseFailure(parser, INVALID_REMINDER_DESC + REMINDER_DESC_DATE + REMINDER_DESC_TIME,
                expectedMessage);

        //invalid date
        assertParseFailure(parser, REMINDER_DESC_COFFEE + INVALID_REMINDER_DESC_DATE + REMINDER_DESC_TIME,
                DueDate.DUEDATE_FORMAT_MESSAGE);

        //invalid time
        assertParseFailure(parser, REMINDER_DESC_COFFEE + REMINDER_DESC_DATE  + INVALID_REMINDER_DESC_TIME,
                DueDate.DUEDATE_FORMAT_MESSAGE);
    }
}
