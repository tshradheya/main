package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NICKNAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nickname;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

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
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person person = createEditedPerson(personToEdit, nickname);

        try {

        } catch () {

        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SET_NICKNAME_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code nickname}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit, Nickname nickname) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Set<Tag> tags = personToEdit.getTags();

        return new Person(name, phone, email, address, nickname, tags);
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
