package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Theme;

public class ThemeCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "theme";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the theme of the address book\n"
            + "Parameter: THEME\n"
            + "Example: " + COMMAND_WORD + " dark";

    private final Theme theme;

    public static final String MESSAGE_SET_THEME_SUCCESS = "Successfully set theme: %1$s";

    public ThemeCommand(Theme theme) {
        this.theme = theme;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(String.format(MESSAGE_SET_THEME_SUCCESS, theme.getTheme()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ThemeCommand)) {
            return false;
        }

        // state check
        ThemeCommand e = (ThemeCommand) other;
        return theme.equals(e.theme);
    }
}
