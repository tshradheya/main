package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

public class ThemeCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "theme";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the theme of the address book\n"
            + "Parameter: [THEME]\n"
            + "Example: " + COMMAND_WORD + " dark";

    public static final String MESSAGE_SET_THEME_SUCCESS = "Successfully set Theme: %1$s";
    public static final String MESSAGE_THEME_NOT_EXISTS = "There is no such theme.";

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException("Nothing happened!");
    }
}
