package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.UnsortedPersons.getUnsortedAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SortCommandTest {
    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getUnsortedAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        sortCommand = new SortCommand();
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void executeSortUnsortedToSorted() {
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
