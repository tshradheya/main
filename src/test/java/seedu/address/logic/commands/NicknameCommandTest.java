package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NICKNAME_AMY;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
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
}
