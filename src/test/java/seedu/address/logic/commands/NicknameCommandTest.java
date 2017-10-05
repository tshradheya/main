package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NICKNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NICKNAME_BOB;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class NicknameCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeUndoableCommand() throws Exception {
        boolean thrown = false;

        NicknameCommand command = new NicknameCommand(INDEX_FIRST_PERSON, VALID_NICKNAME_AMY);

        try {
            command.executeUndoableCommand();
        } catch (CommandException ce) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void equals() {
        final NicknameCommand standardCommand = new NicknameCommand(INDEX_FIRST_PERSON, VALID_NICKNAME_AMY);
        final NicknameCommand commandWithSameValues = new NicknameCommand(INDEX_FIRST_PERSON, VALID_NICKNAME_AMY);
        final NicknameCommand commandWithDifferentIndex = new NicknameCommand(INDEX_SECOND_PERSON, VALID_NICKNAME_AMY);
        final NicknameCommand commandWithDifferentNickname = new NicknameCommand(INDEX_FIRST_PERSON, VALID_NICKNAME_BOB);
        final NicknameCommand commandWithDifferentValues = new NicknameCommand(INDEX_SECOND_PERSON, VALID_NICKNAME_BOB);

        // same object -> Returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different object, same type with same values -> return true
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same type but different index -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

        // same type but different nickname -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentNickname));

        // same type but different index and nickname -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentValues));
    }
}
