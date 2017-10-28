package systemtests;

import static seedu.address.logic.commands.SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.Model;

public class PopularContactsSystemTest extends AddressBookSystemTest {

    @Test
    public void favouriteContactsTest() {
        String command = "   " + SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredListPopularContacts(expectedModel);
        assertCommandSuccessForSelect(command, INDEX_FIRST_PERSON);
        assertSelectedCardChanged(INDEX_FIRST_PERSON);

    }

    /**
     * Command success for Select
     * @param command
     * @param expectedSelectedCardIndex
     */
    private void assertCommandSuccessForSelect(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
                MESSAGE_SELECT_PERSON_SUCCESS, expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getPersonListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedCardUnchanged();
        } else {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

}


