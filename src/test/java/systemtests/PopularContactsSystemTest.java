package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.Model;

public class PopularContactsSystemTest extends AddressBookSystemTest {

    @Test
    public void favouriteContactsTest() {
        String command = "   " + SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredListPopularContacts(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

    }

    /**
     * Asserts command success
     * @param command to be executed
     * @param expectedModel the model expected
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }
}
