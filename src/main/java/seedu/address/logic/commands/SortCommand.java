package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Sorts the current list in alphabetical order
 */

public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the current list in alphabetical order "
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Sort command not implemented yet";

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}

