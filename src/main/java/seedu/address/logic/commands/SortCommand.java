package seedu.address.logic.commands;

import javafx.collections.transformation.SortedList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.Comparator;

/**
 * Sorts the current list in alphabetical order
 */

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the current list in alphabetical order "
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "List Sorted";

    @Override
    public CommandResult execute() {

        model.sortFilteredPersonList();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}

