package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NICKNAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Nickname;

public class NicknameCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "nickname";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the nickname to the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NICKNAME + "NICKNAME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NICKNAME + "Adam";

    public static final String MESSAGE_SET_NICKNAME_SUCCESS = "Nickname set to Person: %1$s";

    private final Index index;
    private final Nickname nickname;

    /**
     * @param index of the person in the filtered person list to edit
     * @param nickname details to edit the person with
     */
    public NicknameCommand(Index index, Nickname nickname) {
        requireNonNull(index);
        requireNonNull(nickname);

        this.index = index;
        this.nickname = nickname;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(index.getOneBased() + " " + nickname);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NicknameCommand)) {
            return false;
        }

        // state check
        NicknameCommand e = (NicknameCommand) other;
        return index.equals(e.index) && nickname.equals(e.nickname);
    }
}
