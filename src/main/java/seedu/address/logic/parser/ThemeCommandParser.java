//@@author chuaweiwen
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_THEME;

import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ThemeCommand object
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ThemeCommand
     * and returns an ThemeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ThemeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
        }

        switch(trimmedArgs) {
        case ThemeNames.THEME_DARK:
            return new ThemeCommand(new Theme(trimmedArgs, ThemeNames.THEME_DARK_CSS));

        case ThemeNames.THEME_SKY:
            return new ThemeCommand(new Theme(trimmedArgs, ThemeNames.THEME_SKY_CSS));

        default:
            throw new ParseException(
                String.format(MESSAGE_UNKNOWN_THEME, ThemeCommand.MESSAGE_USAGE));
        }
    }
}
//@@author
