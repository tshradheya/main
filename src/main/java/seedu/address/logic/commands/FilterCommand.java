package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name and/or tags contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names and tags contain"
            + "any of the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [n/NAME] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " n/Alex t/Friends";

    //private final NameContainsKeywordsPredicate predicate;

    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
